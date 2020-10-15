package com.king.producer;

import com.king.configurations.Executor;
import com.king.constant.Constants;
import com.king.model.Event;
import com.king.configurations.Configurations;
import com.king.utils.EventFilter;
import com.king.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by kaktas on 14/10/2020
 */
public class QueueProducer implements Runnable {

    private static final Logger log = Logger.getLogger(QueueProducer.class.getName());

    private final String inputDirectory;
    private final String outputDirectory;
    private final BlockingQueue<Event> queue;

    public QueueProducer(final BlockingQueue<Event> queue, final String inputDirectory, final String outputDirectory) {
        this.queue = queue;
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void run() {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Objects.requireNonNull(Paths.get(inputDirectory)))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path ->
                            futures.add(CompletableFuture.runAsync(() -> processInputFile(path), Executor.executor)));
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            Executor.executor.shutdown();
        }

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                .thenRunAsync(() -> {
                    try {
                        log.info("Queue marker is being added to queue to stop consumers.");
                        queue.put(new Event());
                    } catch (InterruptedException e) {
                        log.severe(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }, Executor.executor)
                .thenRun(() -> Executor.executor.shutdown()).join();
    }

    private void processInputFile(Path file) {
        log.info("Reading file : " + file.toString());

        try (Stream<String> stream = Files.lines(Paths.get(file.toString()))) {
            stream.forEach(event -> {
                try {
                    log.info("New event is arrived:" + event);
                    Thread.sleep(Configurations.producerDelay());
                    if (isEventValid(event)) {
                        log.info("Event is valid, putting into the queue.");
                        queue.put(createEvent(event));
                    } else {
                        log.info("Event is not valid. It will be written to " + Constants.INVALID_LOG_FILE_NAME);
                        Utils.writeToFile(outputDirectory + "/" + Constants.INVALID_LOG_FILE_NAME, (event + "\n"));
                    }

                } catch (InterruptedException | IOException e) {
                    log.severe(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            });
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private boolean isEventValid(String event) {
        String[] eventArray = event.split(Constants.EVENT_DELIMITER);

        //null-check for event items
        if (EventFilter.isNull(eventArray))
            return false;

        //event name check
        String eventPattern = Configurations.getEvents().get(eventArray[0]);
        if (eventPattern == null || eventPattern.isBlank())
            return false;

        //check event against its pattern
        if (!EventFilter.isPatternMatched(eventArray, eventPattern.split(Constants.EVENT_DELIMITER))) {
            return false;
        }

        //check if the game exists
        if (Configurations.getGames().get(eventArray[2]) == null) {
            return false;
        }

        return true;
    }

    private Event createEvent(String event) {
        String[] eventArray = event.split(Constants.EVENT_DELIMITER);

        Event eventObj = new Event();
        eventObj.setEventName(eventArray[0]);
        eventObj.setUserId(eventArray[1]);
        eventObj.setGameId(eventArray[2]);
        if (eventArray.length == 5)
            eventObj.setProductDescription(eventArray[3]);
        eventObj.setInstallationDateTime(eventArray[eventArray.length - 1]);

        return eventObj;
    }
}

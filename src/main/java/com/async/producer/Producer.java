package com.async.producer;

import com.async.configurations.Executor;
import com.async.constant.Constants;
import com.async.model.Event;
import com.async.configurations.Configurations;
import com.async.utils.EventFilter;
import com.async.utils.Utils;
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

public class Producer implements Runnable {

    private static final Logger log = Logger.getLogger(Producer.class.getName());

    private final String inputDirectory;
    private final String outputDirectory;
    private final BlockingQueue<Event> queue;

    public Producer(final BlockingQueue<Event> queue, final String inputDirectory, final String outputDirectory) {
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
                            futures.add(CompletableFuture.runAsync(() -> processInputFile(path), Executor.customExecutor)));
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            Executor.customExecutor.shutdown();
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
                }, Executor.customExecutor)
                .thenRun(Executor.customExecutor::shutdown).join();
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

        //null-check for event items TODO: replace with lambda
        if (EventFilter.isNull(eventArray))
            return false;

        //event name check TODO: replace with lambda
        String eventPattern = Configurations.getEvents().get(eventArray[0]);
        if (eventPattern == null || eventPattern.isBlank())
            return false;

        //check event against its pattern
        if (!EventFilter.isPatternMatched(eventArray, eventPattern.split(Constants.EVENT_DELIMITER))) {
            return false;
        }

        //check if the app exists
        if (Configurations.getApps().get(eventArray[2]) == null) {
            return false;
        }

        return true;
    }

    private Event createEvent(String event) {
        String[] eventArray = event.split(Constants.EVENT_DELIMITER);

        Event eventObj = new Event();
        eventObj.setEventName(eventArray[0]);
        eventObj.setUserId(eventArray[1]);
        eventObj.setAppId(eventArray[2]);
        if (eventArray.length == 5)
            eventObj.setProductDescription(eventArray[3]);
        eventObj.setInstallationDateTime(eventArray[eventArray.length - 1]);

        return eventObj;
    }
}

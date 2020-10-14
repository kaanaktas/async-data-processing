package com.king.producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by kaktas on 14/10/2020
 */
public class QueueProducer implements Runnable {

    private static final Logger log = Logger.getLogger(QueueProducer.class.getName());
    private static final String DELIMETER = "|";

    private final String inputDirectory;
    private BlockingQueue<String> queue;

    public QueueProducer(final BlockingQueue<String> queue, final String inputDirectory) {
        this.queue = queue;
        this.inputDirectory = inputDirectory;
    }

    @Override
    public void run() {
        try (Stream<Path> paths = Files.walk(Objects.requireNonNull(Paths.get(inputDirectory)))) {
            paths.filter(Files::isRegularFile).forEach(this::processInputFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void processInputFile(Path file) {
        log.info("Reading file : " + file.toString());

        try (Stream<String> stream = Files.lines(Paths.get(file.toString()))) {
            stream.forEach(message -> {
                try {
                    log.info("New message is arrived:" + message);
                    Thread.sleep(1000);

                    queue.put(message);
                } catch (InterruptedException e) {
                    log.severe(e.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEventValid(String message) {
        String[] partialMessage = message.split(DELIMETER);

        if (partialMessage.length != 4 && partialMessage.length != 5) {
            return false;
        }

        if (isNull(partialMessage))
            return false;

        if (isNotNumeric(partialMessage[2], partialMessage[partialMessage.length - 1])) {
            return false;
        }

        return true;
    }

    private boolean isNull(String... inputs) {
        for (String input : inputs) {
            if (input == null || "".equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNumeric(String... numerics) {
        for (String numeric : numerics) {
            try {
                Double.parseDouble(numeric);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidDate(String date) {

        return true;
    }
}

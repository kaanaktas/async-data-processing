package com.async.consumer;

import com.async.configurations.Configurations;
import com.async.model.Event;
import com.async.utils.Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    private static final Logger log = Logger.getLogger(Consumer.class.getName());

    private final BlockingQueue<Event> queue;
    private final String outputDirectory;

    public Consumer(BlockingQueue<Event> queue, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Event event;

            while ((event = queue.take()).getEventName() != null) {
                Thread.sleep(Configurations.consumerDelay());
                log.info("Consumed " + event.toString());
                convertEvent(event);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void convertEvent(Event event) throws NoSuchAlgorithmException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(Utils.hashData(event.getUserId())).append("\"").append(",");
        sb.append("\"").append(event.getAppId()).append("\"").append(",");
        if (event.getProductDescription() != null){
            sb.append("\"").append(event.getProductDescription()).append("\"").append(",");
        }
        sb.append("\"").append(event.getInstallationDateTime()).append("\"");

        Utils.writeToFile(outputDirectory+"/"+event.getEventName()+".csv", sb.toString()+"\n");
    }

}
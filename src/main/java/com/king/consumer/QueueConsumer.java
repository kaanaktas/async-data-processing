package com.king.consumer;

import com.king.model.Event;
import com.king.utils.Utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class QueueConsumer implements Runnable {
    private static final Logger log = Logger.getLogger(QueueConsumer.class.getName());

    private final BlockingQueue<Event> queue;
    private final String outputDirectory;

    public QueueConsumer(BlockingQueue<Event> queue, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Event event;

            while ((event = queue.take()).getEventName() != null) {
                Thread.sleep(100);
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
        sb.append("\"").append(event.getGameId()).append("\"").append(",");
        if (event.getProductDescription() != null){
            sb.append("\"").append(event.getProductDescription()).append("\"").append(",");
        }
        sb.append("\"").append(event.getInstallationDateTime()).append("\"");

        Utils.writeToFile(outputDirectory+"/"+event.getEventName()+".csv", sb.toString()+"\n");
    }

}
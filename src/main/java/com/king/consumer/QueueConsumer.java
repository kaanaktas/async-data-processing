package com.king.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class QueueConsumer implements Runnable {
    private static final Logger log = Logger.getLogger(QueueConsumer.class.getName());
    private final BlockingQueue<String> queue;

    public QueueConsumer(BlockingQueue<String> queue, String outputDirectory) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String msg;

            while (!(msg = queue.take()).equals("exit")) {
                Thread.sleep(10);
                log.info("Consumed " + msg);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package com.king.services;

import com.king.consumer.QueueConsumer;
import com.king.model.Event;
import com.king.producer.QueueProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class StreamManagerImpl implements StreamManager {

    private static final Logger log = Logger.getLogger(StreamManagerImpl.class.getName());

    private final QueueProducer queueProducer;
    private final QueueConsumer queueConsumer;

    public StreamManagerImpl(String inputDirectory, String outputDirectory) {
        BlockingQueue<Event> queue = new LinkedBlockingDeque<>();

        queueProducer = new QueueProducer(queue, inputDirectory, outputDirectory);
        queueConsumer = new QueueConsumer(queue, outputDirectory);
    }

    @Override
    public void manageStream() {
        new Thread(queueProducer).start();
        new Thread(queueConsumer).start();
        log.info("Data streaming has been started...");
    }
}

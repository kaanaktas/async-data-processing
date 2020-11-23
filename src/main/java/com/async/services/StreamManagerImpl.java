package com.async.services;

import com.async.configurations.Executor;
import com.async.consumer.Consumer;
import com.async.model.Event;
import com.async.producer.Producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class StreamManagerImpl implements StreamManager {

    private static final Logger log = Logger.getLogger(StreamManagerImpl.class.getName());

    private final Producer producer;
    private final Consumer consumer;

    public StreamManagerImpl(String inputDirectory, String outputDirectory) {
        BlockingQueue<Event> queue = new LinkedBlockingDeque<>();

        producer = new Producer(queue, inputDirectory, outputDirectory);
        consumer = new Consumer(queue, outputDirectory);
    }

    @Override
    public void manageStream() {
        Executor.customExecutor.execute(producer);
        Executor.customExecutor.execute(consumer);

        log.info("Data streaming has been started...");
    }
}

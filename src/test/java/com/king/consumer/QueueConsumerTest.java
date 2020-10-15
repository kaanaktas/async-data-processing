package com.king.consumer;

import com.king.configurations.Executor;
import com.king.model.Event;
import com.king.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 15/10/2020
 */
class QueueConsumerTest {

    static String inputDir = "./src/test/resources/test/input";
    static String outputDir = "./src/test/resources/test/output";
    static String outputFullPath;

    QueueConsumer consumer;
    BlockingQueue<Event> queue;


    @BeforeEach
    void setUp() throws IOException, InterruptedException {
//        outputFullPath = outputDir + "/" + Constants.INVALID_LOG_FILE_NAME;
        TestUtils.cleanDirectory(outputDir);
        Executor.executor = Executors.newFixedThreadPool(3);
        queue = new LinkedBlockingDeque<>();
        initializeQueue();

    }

    @Test
    void consumerCountValidEventNumber() throws InterruptedException {
        consumer = new QueueConsumer(queue, outputDir);
        Executor.executor.execute(consumer);

        assertEquals(3, queue.size());

        while (!queue.isEmpty()) {
        }

        assertTrue(queue.isEmpty());
    }

    private void initializeQueue() throws InterruptedException {
        Event event1 = new Event();
        event1.setEventName("gameinstallation");
        event1.setUserId("jane");
        event1.setGameId("1");
        event1.setInstallationDateTime("1587583122");

        Event event2 = new Event();
        event2.setEventName("gameinstallation");
        event2.setUserId("jane");
        event2.setGameId("1");
        event2.setInstallationDateTime("1587583122");

        queue.put(event1);
        queue.put(event2);
        queue.put(new Event());
    }
}
package com.king.consumer;

import com.king.configurations.Executor;
import com.king.constant.TimeConstants;
import com.king.model.Event;
import com.king.utils.Const;
import com.king.utils.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 15/10/2020
 */
class QueueConsumerTest {

    private BlockingQueue<Event> queue;

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        TestsUtils.cleanDirectory(Const.outputDirectory);
        Executor.executor = Executors.newFixedThreadPool(3);
        queue = new LinkedBlockingDeque<>();
        initializeQueue();
    }

    @Timeout(TimeConstants.ONE_SECOND * 3)
    @Test
    void consumerCountValidEventNumber() throws IOException {
        QueueConsumer consumer = new QueueConsumer(queue, Const.outputDirectory);
        Executor.executor.execute(consumer);

        assertEquals(4, queue.size());

        while (!queue.isEmpty()) {
        }

        assertTrue(queue.isEmpty());

        List<String> recordsInst = TestsUtils.getRecordsFromFile(Const.outputDirectory + "/gameinstallation.csv");
        assertEquals(2, recordsInst.size());

        List<String> recordsPurch = TestsUtils.getRecordsFromFile(Const.outputDirectory + "/gamepurchase.csv");
        assertEquals(1, recordsPurch.size());
        String[] recordPurchArray = recordsPurch.get(0).split(",");
        assertEquals(4, recordPurchArray.length);
        assertEquals("\"81f8f6dde88365f3928796ec7aa53f72820b06db8664f5fe76a7eb13e24546a2\"", recordPurchArray[0]);
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

        Event event3 = new Event();
        event3.setEventName("gamepurchase");
        event3.setUserId("jane");
        event3.setGameId("1");
        event3.setProductDescription("1 Free Life");
        event3.setInstallationDateTime("1587583122");

        queue.put(event1);
        queue.put(event2);
        queue.put(event3);
        queue.put(new Event());
    }
}
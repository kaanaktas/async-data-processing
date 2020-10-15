package com.king.producer;

import com.king.configurations.Executor;
import com.king.constant.Constants;
import com.king.constant.TimeConstants;
import com.king.model.Event;
import com.king.utils.Const;
import com.king.utils.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by kaktas on 15/10/2020
 */
class QueueProducerTest {
    static String outputFullPath;

    QueueProducer producer;
    BlockingQueue<Event> queue;

    @BeforeEach
    void setUp() throws IOException {
        Executor.executor = Executors.newFixedThreadPool(3);
        outputFullPath = Const.outputDirectory + "/" + Constants.INVALID_LOG_FILE_NAME;
        TestsUtils.cleanDirectory(Const.outputDirectory);
        queue = new LinkedBlockingDeque<>();
    }

    @Test
    void producerCountEventNumber() throws InterruptedException, IOException {
        producer = new QueueProducer(queue, Const.inputDirectory, Const.outputDirectory);
        Executor.executor.execute(producer);

        int counter = 0;
        while (queue.take().getEventName() != null) {
            counter++;
        }

        List<String> invalidRecords = TestsUtils.getRecordsFromFile(outputFullPath);
        assertEquals(14, invalidRecords.size());
        assertEquals(11, counter);
    }

    @Test
    @Timeout(TimeConstants.ONE_SECOND * 5)
    void producerWrongInputDirectory() {
        producer = new QueueProducer(queue, "inputDir", Const.outputDirectory);
        Executor.executor.execute(producer);

        while (true) {
            if (Executor.executor.isShutdown())
                break;
        }

        assertTrue(Executor.executor.isShutdown());
    }
}
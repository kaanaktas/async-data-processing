package com.king.producer;

import com.king.configurations.Executor;
import com.king.constant.Constants;
import com.king.constant.TimeConstants;
import com.king.model.Event;
import com.king.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by kaktas on 15/10/2020
 */
class QueueProducerTest {

    static String inputDir = "./src/test/resources/test/input";
    static String outputDir = "./src/test/resources/test/output";
    static String outputFullPath;

    QueueProducer producer;
    BlockingQueue<Event> queue;

    @BeforeEach
    void setUp() throws IOException {
        Executor.executor = Executors.newFixedThreadPool(3);
        outputFullPath = outputDir + "/" + Constants.INVALID_LOG_FILE_NAME;
        TestUtils.cleanDirectory(outputDir);
        queue = new LinkedBlockingDeque<>();
    }

    @Test
    void producerCountEventNumber() throws InterruptedException, IOException {
        producer = new QueueProducer(queue, inputDir, outputDir);
        Executor.executor.execute(producer);

        int counter = 0;
        while (queue.take().getEventName() != null) {
            counter++;
        }

        List<String> invalidRecords = Files.readAllLines(TestUtils.getPath(outputFullPath));
        assertEquals(3, invalidRecords.size());
        assertEquals(2, counter);
    }

    @Test
    @Timeout(TimeConstants.ONE_SECOND * 5)
    void producerWrongInputDirectory() {
        producer = new QueueProducer(queue, "inputDir", outputDir);
        Executor.executor.execute(producer);

        while (true) {
            if (Executor.executor.isShutdown())
                break;
        }

        assertTrue(Executor.executor.isShutdown());
    }
}
package com.async.producer;

import com.async.configurations.Executor;
import com.async.constant.Constants;
import com.async.constant.TimeConstants;
import com.async.model.Event;
import com.async.utils.Const;
import com.async.utils.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProducerTest {
    static String outputFullPath;

    Producer producer;
    BlockingQueue<Event> queue;

    @BeforeEach
    void setUp() throws IOException {
        Executor.customExecutor = Executors.newFixedThreadPool(5);
        outputFullPath = Const.outputDirectory + "/" + Constants.INVALID_LOG_FILE_NAME;
        TestsUtils.cleanDirectory(Const.outputDirectory);
        queue = new LinkedBlockingDeque<>();
    }

    @Test
    void producerCountEventNumber() throws InterruptedException, IOException {
        producer = new Producer(queue, Const.inputDirectory, Const.outputDirectory);
        Executor.customExecutor.execute(producer);

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
        producer = new Producer(queue, "inputDir", Const.outputDirectory);
        Executor.customExecutor.execute(producer);

        while (true) {
            if (Executor.customExecutor.isShutdown())
                break;
        }

        assertTrue(Executor.customExecutor.isShutdown());
    }
}
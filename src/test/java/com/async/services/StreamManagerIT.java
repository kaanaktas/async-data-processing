package com.async.services;

import com.async.configurations.Executor;
import com.async.constant.TimeConstants;
import com.async.utils.Const;
import com.async.utils.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamManagerIT {

    StreamManager streamManager;

    @BeforeEach
    void setUp() throws IOException {
        TestsUtils.cleanDirectory(Const.outputDirectory);
        streamManager = new StreamManagerImpl(Const.inputDirectory, Const.outputDirectory);
    }

    @Timeout(TimeConstants.ONE_SECOND * 10)
    @Test
    void manageStream() throws IOException {
        streamManager.manageStream();

        while(!Executor.customExecutor.isTerminated()){}

        assertTrue(Executor.customExecutor.isTerminated());


        //check if all expected files has been created
        List<File> countOfOutputFile = TestsUtils.runFiles(Const.outputDirectory);
        assertEquals(5, countOfOutputFile.size());

        //check if all events puts into a file
        int recordCounter = 0;
        for (File file : countOfOutputFile) {
            List<String> records = TestsUtils.getRecordsFromFile(file.getPath());
            if(!records.isEmpty())
                recordCounter += records.size();
        }

        assertEquals(25, recordCounter);

    }
}
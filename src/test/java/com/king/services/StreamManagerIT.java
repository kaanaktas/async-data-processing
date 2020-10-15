package com.king.services;

import com.king.configurations.Executor;
import com.king.constant.TimeConstants;
import com.king.utils.Const;
import com.king.utils.TestsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by kaktas on 14/10/2020
 */

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

        while(!Executor.executor.isTerminated()){}

        assertTrue(Executor.executor.isTerminated());


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
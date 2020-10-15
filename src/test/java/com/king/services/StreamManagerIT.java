package com.king.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by kaktas on 14/10/2020
 */

class StreamManagerIT {

    private static final String inputDirectory = "documents/data-processing-v3-java/input";
    private static final String outputDirectory = "documents/data-processing-v3-java/test.output";

    StreamManager streamManager;

    @BeforeEach
    void setUp() {
        streamManager = new StreamManagerImpl(inputDirectory, outputDirectory);
    }

    @Test
    void manageStream() {
        streamManager.manageStream();
    }
}
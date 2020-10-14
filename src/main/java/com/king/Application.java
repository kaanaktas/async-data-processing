package com.king;

import com.king.services.StreamManager;
import com.king.services.StreamManagerImpl;

/**
 * Created by kaktas on 14/10/2020
 */
public class Application {

    public static void main(String[] args) {
        String inputDirectory = "documents/data-processing-v3-java/input1";
        String outputDirectory = "documents/data-processing-v3-java/output";

        StreamManager streamManager = new StreamManagerImpl(inputDirectory, outputDirectory);
        streamManager.manageStream();
    }
}

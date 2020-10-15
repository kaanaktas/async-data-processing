package com.king;

import com.king.services.StreamManager;
import com.king.services.StreamManagerImpl;

import java.nio.file.*;

/**
 * Created by kaktas on 14/10/2020
 */
public class Application {
    private static String inputDirectory = "documents/data-processing-v3-java/input";
    private static String outputDirectory = "documents/data-processing-v3-java/output2";

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            inputDirectory = args[0];
            outputDirectory = args[1];
        }

        Path inputPath = Paths.get(inputDirectory);
        if (!Files.exists(inputPath)) {
            throw new NotDirectoryException(inputDirectory + " doesn't exist!");
        }

        Path outputPath = Paths.get(outputDirectory);
        if (Files.exists(outputPath)) {
            throw new DirectoryNotEmptyException(outputDirectory + " already exists!");
        } else{
            Files.createDirectories(outputPath);
        }

        StreamManager streamManager = new StreamManagerImpl(inputDirectory, outputDirectory);
        streamManager.manageStream();
    }
}

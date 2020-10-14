package com.king.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class Utils {

    private static final Logger log = Logger.getLogger(Thread.currentThread().getContextClassLoader().getClass().getName());

    private Utils() {
    }

    public static void writeToFile(String filepath, String content) throws IOException {
        try {
            Files.write(Paths.get(filepath), content.getBytes());
            log.info("Content has been written to " + filepath + " successfully.");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

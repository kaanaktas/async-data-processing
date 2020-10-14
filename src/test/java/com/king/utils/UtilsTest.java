package com.king.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 14/10/2020
 */
class UtilsTest {

    @Test
    void writeToFile() throws IOException {
        Path path = Paths.get("test_file");
        Utils.writeToFile("test_file", "testdata");
        assertTrue(Files.exists(path));
        Files.delete(path);
    }
}
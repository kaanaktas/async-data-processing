package com.async.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    void writeToFileTest() throws IOException {
        Path path = Paths.get("test_file");
        Utils.writeToFile("test_file", "testdata");
        assertTrue(Files.exists(path));
        Files.delete(path);
    }

    @Test
    void hashDataTest() throws NoSuchAlgorithmException {
        String clearText = "jane";
        String result = Utils.hashData(clearText);
        assertEquals("81f8f6dde88365f3928796ec7aa53f72820b06db8664f5fe76a7eb13e24546a2", result);
    }
}
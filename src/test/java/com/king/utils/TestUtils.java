package com.king.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 * Created by kaktas on 15/10/2020
 */
public class TestUtils {

    public static void cleanDirectory(String file) throws IOException {
        Path path = getPath(file);
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(f -> {
                        if (f.exists())
                            f.delete();
                    });
        }
        Files.createDirectories(path);
    }

    public static Path getPath(String file) {
        return Paths.get(file);
    }
}

package com.async.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestsUtils {

    public static void cleanDirectory(String file) throws IOException {
        Path path = getPath(file);
        if (Files.exists(path)) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.createDirectories(path);
    }

    public static List<File> runFiles(String file) throws IOException {
        return Files.walk(getPath(file))
                .sorted(Comparator.reverseOrder())
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    public static List<String> getRecordsFromFile(String file) throws IOException {
        return Files.readAllLines(TestsUtils.getPath(file));
    }

    public static Path getPath(String file) {
        return Paths.get(file);
    }
}

package com.king.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class Utils {

    private static final Logger log = Logger.getLogger(Utils.class.getName());
    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    private Utils() {
    }

    public static void writeToFile(String filepath, String content) throws IOException {
        try {

            OpenOption[] options = new OpenOption[]{StandardOpenOption.APPEND, StandardOpenOption.CREATE};
            Files.write(Paths.get(filepath), content.getBytes(), options);
            log.info("Content has been written to " + filepath + " successfully.");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static String hashData(String clearText) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(clearText.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash);

    }

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}

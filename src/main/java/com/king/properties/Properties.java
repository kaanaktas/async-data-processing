package com.king.properties;

import com.king.constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by kaktas on 11/10/2020
 */
public class Properties {
    private static final Logger log = Logger.getLogger(Properties.class.getName());
    private static final java.util.Properties properties = new java.util.Properties();

    private Properties() {
    }

    static {
        try (InputStream inputStream = Properties.class.getResourceAsStream(Constants.propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.severe("Couldn't find the file: " + e.getMessage());
        }
    }

    public static Map<String, String> getGameProperties() {
        return properties.entrySet().stream()
                .filter(p -> p.getKey().toString().startsWith("game."))
                .collect(Collectors.toMap(k -> k.getKey().toString().split("\\.")[1], k -> k.getValue().toString()));
    }

}

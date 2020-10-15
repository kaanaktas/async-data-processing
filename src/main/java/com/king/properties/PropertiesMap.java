package com.king.properties;

import com.king.constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by kaktas on 11/10/2020
 */
public class PropertiesMap {
    private static final Logger log = Logger.getLogger(PropertiesMap.class.getName());
    private static final Properties properties = new Properties();
    private static Map<String, String> gameMap = new HashMap<>();
    private static Map<String, String> eventMap = new HashMap<>();


    private PropertiesMap() {
    }

    static {
        try (InputStream inputStream = PropertiesMap.class.getResourceAsStream(Constants.PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.severe("Couldn't find the file: " + e.getMessage());
        }
    }

    public static Map<String, String> getGames() {
        if (gameMap.isEmpty())
            gameMap = properties.entrySet().stream()
                    .filter(p -> p.getKey().toString().startsWith("game."))
                    .collect(Collectors.toMap(k -> k.getKey().toString().split("\\.")[1],
                            k -> k.getValue().toString()));

        return gameMap;
    }

    public static Map<String, String> getEvents() {
        if (eventMap.isEmpty())
            eventMap = properties.entrySet().stream()
                    .filter(p -> p.getKey().toString().startsWith("event."))
                    .collect(Collectors.toMap(k -> k.getKey().toString().split("\\.")[1], k -> k.getValue().toString()));

        return eventMap;
    }

}

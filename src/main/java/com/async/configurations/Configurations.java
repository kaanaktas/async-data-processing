package com.async.configurations;

import com.async.constant.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Configurations {
    private static final Logger log = Logger.getLogger(Configurations.class.getName());
    private static final Properties properties = new Properties();
    private static Map<String, String> appMap = new HashMap<>();
    private static Map<String, String> eventMap = new HashMap<>();


    private Configurations() {
    }

    static {
        try (InputStream inputStream = Configurations.class.getResourceAsStream(Constants.PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.severe("Couldn't find the file: " + e.getMessage());
        }
    }

    public static Map<String, String> getApps() {
        if (appMap.isEmpty())
            appMap = properties.entrySet().stream()
                    .filter(p -> p.getKey().toString().startsWith("app."))
                    .collect(Collectors.toMap(k -> k.getKey().toString().split("\\.")[1],
                            k -> k.getValue().toString()));

        return appMap;
    }

    public static Map<String, String> getEvents() {
        if (eventMap.isEmpty())
            eventMap = properties.entrySet().stream()
                    .filter(p -> p.getKey().toString().startsWith("event."))
                    .collect(Collectors.toMap(k -> k.getKey().toString().split("\\.")[1], k -> k.getValue().toString()));

        return eventMap;
    }

    public static int consumerDelay() {
        return Integer.parseInt(properties.get("consumer.delay").toString());
    }

    public static int producerDelay() {
        return Integer.parseInt(properties.get("producer.delay").toString());
    }

}

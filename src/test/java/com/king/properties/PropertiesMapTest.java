package com.king.properties;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 14/10/2020
 */
class PropertiesMapTest {

    @Test
    void getGameProperties() {
        Map<String, String> result = PropertiesMap.getGames();
        assertTrue(result.size() > 0);
    }

    @Test
    void getEvents() {
        Map<String, String> result = PropertiesMap.getEvents();
        assertTrue(result.size() > 0);
    }
}
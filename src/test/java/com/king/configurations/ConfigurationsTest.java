package com.king.configurations;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 14/10/2020
 */
class ConfigurationsTest {

    @Test
    void getGameProperties() {
        Map<String, String> result = Configurations.getGames();
        assertTrue(result.size() > 0);
    }

    @Test
    void getEvents() {
        Map<String, String> result = Configurations.getEvents();
        assertTrue(result.size() > 0);
    }
}
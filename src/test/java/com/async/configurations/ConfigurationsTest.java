package com.async.configurations;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationsTest {

    @Test
    void getAppProperties() {
        Map<String, String> result = Configurations.getApps();
        assertTrue(result.size() > 0);
    }

    @Test
    void getEvents() {
        Map<String, String> result = Configurations.getEvents();
        assertTrue(result.size() > 0);
    }
}
package com.king.properties;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 14/10/2020
 */
class PropertiesTest {

    @Test
    void getGameProperties() {
        Map<String, String> result = Properties.getGameProperties();
        assertTrue(result.size() > 0);
    }
}
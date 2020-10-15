package com.king.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kaktas on 15/10/2020
 */
class EventFilterTest {

    private static final String delimiter = "\\|";

    @Test
    void isPatternMatched_SuccessCase() {
        String[] eventArray = "string|string|numeric|numeric".split(delimiter);
        String[] patternArray = "gameinstallation|jane|1|1587583122".split(delimiter);
        assertTrue(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isPatternMatched_NotMatchNumeric() {
        String[] patternArray = "string|string|numeric|numeric".split(delimiter);
        String[] eventArray = "gameinstallation|jane|1test|1587583122".split(delimiter);
        assertFalse(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isPatternMatched_NotMatchSize() {
        String[] patternArray = "string|string|numeric|numeric|numeric".split(delimiter);
        String[] eventArray = "gameinstallation|jane|1|1587583122".split(delimiter);
        assertFalse(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isNull_MultipleCases() {
        assertFalse(EventFilter.isNull("gameinstallation|jane|1test|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("gameinstallation||1test|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("gameinstallation|jane| |1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("gameinstallation|jane|\n|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("gameinstallation|jane|\t|1587583122".split(delimiter)));
    }

    @Test
    void isNumeric_MultipleCases() {
        assertTrue(EventFilter.isNumeric("101"));
        assertTrue(EventFilter.isNumeric("1587583122"));
        assertTrue(EventFilter.isNumeric(String.valueOf(Long.MIN_VALUE)));
        assertTrue(EventFilter.isNumeric(String.valueOf(Long.MAX_VALUE)));
        assertTrue(EventFilter.isNumeric("001587583122"));

        assertFalse(EventFilter.isNumeric("test"));
        assertFalse(EventFilter.isNumeric("1test"));
        assertFalse(EventFilter.isNumeric("1011.0"));
        assertFalse(EventFilter.isNumeric("1011,0"));
        assertFalse(EventFilter.isNumeric("15875831.22"));
        assertFalse(EventFilter.isNumeric(String.valueOf(Long.MAX_VALUE).concat("1")));
    }
}
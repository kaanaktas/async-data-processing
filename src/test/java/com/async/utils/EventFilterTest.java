package com.async.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventFilterTest {

    private static final String delimiter = "\\|";

    @Test
    void isPatternMatched_SuccessCase() {
        String[] eventArray = "string|string|numeric|numeric".split(delimiter);
        String[] patternArray = "appinstallation|jane|1|1587583122".split(delimiter);
        assertTrue(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isPatternMatched_NotMatchNumeric() {
        String[] patternArray = "string|string|numeric|numeric".split(delimiter);
        String[] eventArray = "appinstallation|jane|1test|1587583122".split(delimiter);
        assertFalse(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isPatternMatched_NotMatchSize() {
        String[] patternArray = "string|string|numeric|numeric|numeric".split(delimiter);
        String[] eventArray = "appinstallation|jane|1|1587583122".split(delimiter);
        assertFalse(EventFilter.isPatternMatched(eventArray, patternArray));
    }

    @Test
    void isNull_MultipleCases() {
        assertFalse(EventFilter.isNull("appinstallation|jane|1test|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("appinstallation||1test|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("appinstallation|jane| |1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("appinstallation|jane|\n|1587583122".split(delimiter)));
        assertTrue(EventFilter.isNull("appinstallation|jane|\t|1587583122".split(delimiter)));
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
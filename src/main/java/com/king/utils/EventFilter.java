package com.king.utils;

import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class EventFilter {

    private EventFilter() {
    }

    private static final Logger log = Logger.getLogger(EventFilter.class.getName());


    public static boolean isPatternMatched(String[] eventArray, String[] patternArray) {
        if (eventArray.length == patternArray.length) {
            for (int i = 0; i < eventArray.length; i++) {
                if ("numeric".equalsIgnoreCase(patternArray[i]) && !isNumeric(eventArray[i])) {
                    log.warning(String.format("Numeric part of the Event hasn't been matched with the pattern. Value:%s. Expected type:%s",
                            eventArray[i], patternArray[i]));
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public static boolean isNull(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.isBlank()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String numeric) {
        try {
            Long.parseLong(numeric);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

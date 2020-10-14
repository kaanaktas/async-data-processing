package com.king.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by kaktas on 14/10/2020
 */
public class Configuration {

    private static final Properties props = new Properties();
    private static final Logger log = Logger.getLogger(Configuration.class.getName());

    static {
        try (InputStream inputStream = Configuration.class.getResourceAsStream("/application.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            log.warning("Couldn't read the file: " + e.getMessage());
        }
    }

    public static int getJobQuantity() {
        return Integer.parseInt(props.getProperty("job_quantity"));
    }

    public static long getJobDelay() {
        return Long.parseLong(props.getProperty("job_delay"));
    }

    public static String getScheduledDate() {
        return props.getProperty("scheduled_date");
    }

}

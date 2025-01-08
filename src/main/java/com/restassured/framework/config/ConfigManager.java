package com.restassured.framework.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        } catch (Exception e) {
            logger.error("Error loading config.properties", e);
        }
    }

    public static String getBaseUrl() {
        return props.getProperty("base.url");
    }

    public static int getRetryCount() {
        return Integer.parseInt(props.getProperty("retry.count", "3"));
    }
}


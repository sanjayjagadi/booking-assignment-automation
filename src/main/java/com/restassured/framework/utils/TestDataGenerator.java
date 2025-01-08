package com.restassured.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restassured.framework.models.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TestDataGenerator {
    private static final Logger logger = LogManager.getLogger(TestDataGenerator.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Booking> getBookingsFromJson() {
        try (InputStream inputStream = TestDataGenerator.class.getClassLoader().getResourceAsStream("testdata/bookings.json")) {
            Booking[] bookings = objectMapper.readValue(inputStream, Booking[].class);
            return Arrays.asList(bookings);
        } catch (Exception e) {
            logger.error("Error reading bookings from JSON", e);
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    public static Booking generateInvalidBooking() {
        Booking booking = getBookingsFromJson().get(0);
        booking.setTotalprice(-100); // Invalid price
        return booking;
    }
}


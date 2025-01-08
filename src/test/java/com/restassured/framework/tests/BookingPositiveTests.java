package com.restassured.framework.tests;

import com.aventstack.extentreports.ExtentTest;
import com.restassured.framework.base.BaseTest;
import com.restassured.framework.listeners.RetryAnalyzer;
import com.restassured.framework.models.Booking;
import com.restassured.framework.reports.ExtentManager;
import com.restassured.framework.utils.APIUtils;
import com.restassured.framework.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BookingPositiveTests extends BaseTest {

    private int createdBookingId;

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
    public void testCreateBooking() {
        ExtentTest test = ExtentManager.createTest("Create Booking - POST API", "Test creating a valid booking using POST API");
        
        logger.info("Testing create booking using POST API");
        test.info("Testing create booking using POST API");
        
        Booking booking = TestDataGenerator.getBookingsFromJson().get(0);
        Response response = APIUtils.postRequest("/booking", booking);

        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
        assertNotNull(response.jsonPath().getInt("bookingid"), "Booking ID should not be null");
        
        createdBookingId = response.jsonPath().getInt("bookingid");
        logger.info("Created booking with ID: " + createdBookingId);
        test.info("Created booking with ID: " + createdBookingId);

        // Validate the response structure
        assertNotNull(response.jsonPath().getMap("booking"), "Booking details should not be null");
        assertEquals(response.jsonPath().getString("booking.firstname"), booking.getFirstname(), "Firstname mismatch in response");
        assertEquals(response.jsonPath().getString("booking.lastname"), booking.getLastname(), "Lastname mismatch in response");
        assertEquals(response.jsonPath().getDouble("booking.totalprice"), booking.getTotalprice(), 0.01, "Total price mismatch in response");
        assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), booking.isDepositpaid(), "Deposit paid mismatch in response");
        assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"), booking.getBookingDates().getCheckin(), "Check-in date mismatch in response");
        assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"), booking.getBookingDates().getCheckout(), "Check-out date mismatch in response");
        assertEquals(response.jsonPath().getString("booking.additionalneeds"), booking.getAdditionalneeds(), "Additional needs mismatch in response");
        
        test.pass("Booking created successfully and response validated");
    }

    @Test(priority = 2, dependsOnMethods = "testCreateBooking", retryAnalyzer = RetryAnalyzer.class)
    public void testGetBooking() {
        ExtentTest test = ExtentManager.createTest("Get Booking - GET API", "Test retrieving a booking using GET API");
        
        logger.info("Testing get booking using GET API");
        test.info("Testing get booking using GET API");
        
        Response getResponse = APIUtils.getRequest("/booking/" + createdBookingId);
        
        assertEquals(getResponse.getStatusCode(), 200, "Unexpected status code when fetching created booking");
        
        Booking originalBooking = TestDataGenerator.getBookingsFromJson().get(0);

        // Validate the retrieved booking details
        assertEquals(getResponse.jsonPath().getString("firstname"), originalBooking.getFirstname(), "Firstname mismatch");
        assertEquals(getResponse.jsonPath().getString("lastname"), originalBooking.getLastname(), "Lastname mismatch");
        assertEquals(getResponse.jsonPath().getDouble("totalprice"), originalBooking.getTotalprice(), 0.01, "Total price mismatch");
        assertEquals(getResponse.jsonPath().getBoolean("depositpaid"), originalBooking.isDepositpaid(), "Deposit paid mismatch");
        assertEquals(getResponse.jsonPath().getString("bookingdates.checkin"), originalBooking.getBookingDates().getCheckin(), "Check-in date mismatch");
        assertEquals(getResponse.jsonPath().getString("bookingdates.checkout"), originalBooking.getBookingDates().getCheckout(), "Check-out date mismatch");
        assertEquals(getResponse.jsonPath().getString("additionalneeds"), originalBooking.getAdditionalneeds(), "Additional needs mismatch");
        
        test.pass("Retrieved booking details validated successfully");
    }
}
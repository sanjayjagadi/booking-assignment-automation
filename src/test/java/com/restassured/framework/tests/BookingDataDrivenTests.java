package com.restassured.framework.tests;

import com.aventstack.extentreports.ExtentTest;
import com.restassured.framework.base.BaseTest;
import com.restassured.framework.listeners.RetryAnalyzer;
import com.restassured.framework.models.Booking;
import com.restassured.framework.reports.ExtentManager;
import com.restassured.framework.utils.APIUtils;
import com.restassured.framework.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class BookingDataDrivenTests extends BaseTest {

    @DataProvider(name = "bookingData")
    public Object[][] bookingDataProvider() {
        return TestDataGenerator.getBookingsFromJson().stream()
                .map(booking -> new Object[]{booking})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "bookingData", retryAnalyzer = RetryAnalyzer.class)
    public void testCreateBooking(Booking booking) {
        ExtentTest test = ExtentManager.createTest("Create Booking - Data Driven", "Test creating a booking with various data");
        
        logger.info("Testing create booking with data: " + booking);
        test.info("Testing create booking with data: " + booking);
        
        Response response = APIUtils.postRequest("/booking", booking);

        assertEquals(response.getStatusCode(), 200, "Unexpected status code");
        assertNotNull(response.jsonPath().getInt("bookingid"), "Booking ID should not be null");
        
        test.pass("Booking created successfully");

        // Validate the created booking
        int bookingId = response.jsonPath().getInt("bookingid");
        Response getResponse = APIUtils.getRequest("/booking/" + bookingId);
        
        assertEquals(getResponse.getStatusCode(), 200, "Unexpected status code when fetching created booking");
        assertEquals(getResponse.jsonPath().getString("firstname"), booking.getFirstname(), "Firstname mismatch");
        assertEquals(getResponse.jsonPath().getString("lastname"), booking.getLastname(), "Lastname mismatch");
        assertEquals(getResponse.jsonPath().getDouble("totalprice"), booking.getTotalprice(), 0.01, "Total price mismatch");
        assertEquals(getResponse.jsonPath().getBoolean("depositpaid"), booking.isDepositpaid(), "Deposit paid mismatch");
        assertEquals(getResponse.jsonPath().getString("bookingdates.checkin"), booking.getBookingDates().getCheckin(), "Check-in date mismatch");
        assertEquals(getResponse.jsonPath().getString("bookingdates.checkout"), booking.getBookingDates().getCheckout(), "Check-out date mismatch");
        assertEquals(getResponse.jsonPath().getString("additionalneeds"), booking.getAdditionalneeds(), "Additional needs mismatch");
        
        test.pass("Created booking details validated successfully");
    }
}


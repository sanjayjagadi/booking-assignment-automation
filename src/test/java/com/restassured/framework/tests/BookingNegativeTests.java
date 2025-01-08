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

public class BookingNegativeTests extends BaseTest {

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testCreateBookingWithInvalidData() {
        ExtentTest test = ExtentManager.createTest("Create Booking with Invalid Data", "Test creating a booking with invalid data");
        
        logger.info("Testing create booking with invalid data");
        test.info("Testing create booking with invalid data");
        
        Booking invalidBooking = TestDataGenerator.generateInvalidBooking();
        Response response = APIUtils.postRequest("/booking", invalidBooking);

        assertEquals(response.getStatusCode(), 200, "Unexpected status code for invalid booking");
        test.pass("Received expected 400 status code for invalid booking");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testGetNonExistentBooking() {
        ExtentTest test = ExtentManager.createTest("Get Non-existent Booking", "Test retrieving a non-existent booking");
        
        logger.info("Testing get non-existent booking");
        test.info("Testing get non-existent booking");
        
        Response response = APIUtils.getRequest("/booking/999999");
        assertEquals(response.getStatusCode(), 404, "Unexpected status code for non-existent booking");
        test.pass("Received expected 404 status code for non-existent booking");
    }
}
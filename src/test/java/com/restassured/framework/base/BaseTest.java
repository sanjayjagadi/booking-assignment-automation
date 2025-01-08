package com.restassured.framework.base;

import com.restassured.framework.config.ConfigManager;
import com.restassured.framework.reports.ExtentManager;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpSuite() {
        ExtentManager.getInstance();
    }

    @BeforeClass
    public void setUp() {
        logger.info("Setting up base URL: " + ConfigManager.getBaseUrl());
        RestAssured.baseURI = ConfigManager.getBaseUrl();
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentManager.endTest();
    }
}


package com.restassured.framework.listeners;

import com.aventstack.extentreports.Status;
import com.restassured.framework.reports.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: " + result.getName());
        ExtentManager.getTest();
        //ExtentManager.getTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getName());
        ExtentManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test failed: " + result.getName());
        ExtentManager.getTest().log(Status.FAIL, "Test failed");
        ExtentManager.getTest().log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test skipped: " + result.getName());
        ExtentManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test suite finished: " + context.getName());
        ExtentManager.getInstance().flush();
    }
}


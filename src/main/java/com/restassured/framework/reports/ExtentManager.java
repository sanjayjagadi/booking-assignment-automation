package com.restassured.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance("test-output/extent-report.html");
        }
        return extent;
    }

    private static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setDocumentTitle("API Test Report");
        htmlReporter.config().setReportName("REST Assured Test Automation Results");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        
        return extent;
    }

    public static ExtentTest getTest() {
        return test.get();
    }
    
//    public static ExtentTest getTest() {
//        return test.get();
//    }

    public static ExtentTest createTest(String name, String description) {
        ExtentTest extentTest = getInstance().createTest(name, description);
        test.set(extentTest);
        return extentTest;
    }

    public static void endTest() {
        getInstance().flush();
    }
}
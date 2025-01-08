package com.restassured.framework.listeners;

import com.restassured.framework.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LogManager.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = ConfigManager.getRetryCount();

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            logger.info("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            retryCount++;
            return true;
        }
        return false;
    }

    private String getResultStatusName(int status) {
        String resultName = null;
        if (status == 1)
            resultName = "SUCCESS";
        if (status == 2)
            resultName = "FAILURE";
        if (status == 3)
            resultName = "SKIP";
        return resultName;
    }
}


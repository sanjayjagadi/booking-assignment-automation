package com.restassured.framework.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class APIUtils {
    private static final Logger logger = LogManager.getLogger(APIUtils.class);

    public static Response postRequest(String endpoint, Object requestBody) {
        logger.info("Sending POST request to: " + endpoint);
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(requestBody);

        Response response = request.post(endpoint);
        logger.info("Received response with status code: " + response.getStatusCode());
        return response;
    }

    public static Response getRequest(String endpoint) {
        logger.info("Sending GET request to: " + endpoint);
        Response response = RestAssured.get(endpoint);
        logger.info("Received response with status code: " + response.getStatusCode());
        return response;
    }
}


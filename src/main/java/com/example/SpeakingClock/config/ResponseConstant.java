package com.example.SpeakingClock.config;

public class ResponseConstant {
    public static final String SUCCESS_RESPONSE = "{\n" +
            "  \"statusCode\": 200,\n" +
            "  \"message\": \"It's eight thirty four\"\n" +
            "}";
    public static final String BAD_REQUEST_RESPONSE = "{\n" +
            "  \"statusCode\": 400,\n" +
            "  \"message\": \"Please pass time in HH:mm format, time should be 24 hours format.\"\n" +
            "}";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "{\n" +
            "  \"statusCode\": 500,\n" +
            "  \"message\": \"Internal Server Error\"\n" +
            "}";
}

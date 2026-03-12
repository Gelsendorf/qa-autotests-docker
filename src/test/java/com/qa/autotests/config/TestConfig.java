package com.qa.autotests.config;

public final class TestConfig {

    private TestConfig() {
    }

    public static String getBaseUrl() {
        return System.getProperty("base.url", System.getenv("BASE_URL") != null
                ? System.getenv("BASE_URL")
                : "https://www.example.com");
    }

    public static String getApiBaseUrl() {
        return System.getProperty("api.base.url", System.getenv("API_BASE_URL") != null
                ? System.getenv("API_BASE_URL")
                : "https://jsonplaceholder.typicode.com");
    }
}

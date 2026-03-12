package com.qa.autotests.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(createDriver());
        }
        return driverThreadLocal.get();
    }

    private static WebDriver createDriver() {
        String seleniumHub = System.getProperty("selenium.hub", System.getenv("SELENIUM_HUB"));
        ChromeOptions options = buildChromeOptions();

        if (seleniumHub != null && !seleniumHub.isEmpty()) {
            logger.info("Connecting to Selenium Grid at: {}", seleniumHub);
            try {
                return new RemoteWebDriver(new URL(seleniumHub), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Invalid Selenium Hub URL: " + seleniumHub, e);
            }
        }

        logger.info("Starting local ChromeDriver");
        return new ChromeDriver(options);
    }

    private static ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("WebDriver instance closed");
        }
    }

    public static void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
    }
}

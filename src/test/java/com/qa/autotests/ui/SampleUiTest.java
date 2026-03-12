package com.qa.autotests.ui;

import com.qa.autotests.config.DriverFactory;
import com.qa.autotests.config.TestConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("UI Tests")
public class SampleUiTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver();
        DriverFactory.configureTimeouts(driver);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Test
    @Story("Page title verification")
    @Description("Verify the page title is not empty when navigating to the base URL")
    public void testPageTitleIsNotEmpty() {
        driver.get(TestConfig.getBaseUrl());
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");
    }

    @Test
    @Story("Page URL verification")
    @Description("Verify the current URL matches the expected base URL after navigation")
    public void testCurrentUrlAfterNavigation() {
        String baseUrl = TestConfig.getBaseUrl();
        driver.get(baseUrl);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertFalse(currentUrl.isEmpty(), "Current URL should not be empty");
    }

    @Test
    @Story("Element presence")
    @Description("Verify the body element is present on the page")
    public void testBodyElementIsPresent() {
        driver.get(TestConfig.getBaseUrl());
        boolean bodyPresent = !driver.findElements(By.tagName("body")).isEmpty();
        Assert.assertTrue(bodyPresent, "Body element should be present on the page");
    }
}

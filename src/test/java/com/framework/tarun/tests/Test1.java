package com.framework.tarun.tests;

import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Utils.BrowserFactory;
import Utils.Config;

public class Test1 {

    Config config = new Config();
    String baseUrl = config.getProperty("baseUrl");
    String browser = config.getProperty("browser");  // Get the browser from config
    WebDriver driver;

    @BeforeTest
    public void openBrowser() {
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException("Browser value is missing in the configuration file.");
        }

        driver = BrowserFactory.getDriver(browser); // Initialize the WebDriver
        if (driver == null) {
            throw new IllegalArgumentException("Failed to initialize the driver for browser: " + browser);
        }
    }

    @Test
    public void testing1() {
        driver.manage().window().maximize();  // Maximize window
        driver.get(baseUrl);  // Navigate to the URL
    }

    @Test
    public void testing2() {
        driver.get(baseUrl);  // Navigate to the URL again
    }

    @AfterTest
    public void quit() {
        if (driver != null) {
            driver.quit();  // Quit the WebDriver after tests
        }
    }
}

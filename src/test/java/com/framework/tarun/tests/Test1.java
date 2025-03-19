package com.framework.tarun.tests;

import org.openqa.selenium.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Utils.BrowserFactory;
import Utils.Config;
import reports.ExtentManager;

public class Test1 {

    Config config = new Config();
    String baseUrl = config.getProperty("baseUrl");
    String browser = config.getProperty("browser");  
    WebDriver driver;
    ExtentTest test;  // Declared globally

    @BeforeTest
    public void openBrowser() {
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException("Browser value is missing in the configuration file.");
        }

        driver = BrowserFactory.getDriver(browser);
        if (driver == null) {
            throw new IllegalArgumentException("Failed to initialize the driver for browser: " + browser);
        }
    }

    @Test
    public void testing1() throws Exception {
        test = ExtentManager.createTest("Url Launch");  // Assign a new test instance
        driver.manage().window().maximize();  
        driver.get(baseUrl);  
        Thread.sleep(2000);   
        test.log(Status.PASS, "Launched URL: " + baseUrl);
    }

    @Test
    public void testing2() {
        test = ExtentManager.createTest("Navigating to other URL");  // Assign a new test instance
        driver.get(baseUrl);  
        test.log(Status.PASS, "Launched URL: " + baseUrl);
    }

    @AfterTest
    public void quit() {
        test = ExtentManager.createTest("Quit Browser");  // Assign a new test instance
        if (driver != null) {
            driver.quit();
            test.log(Status.PASS, "Browser Closed Successfully");
        }
        ExtentManager.flushReports(); // Ensure reports are properly flushed
    }
}

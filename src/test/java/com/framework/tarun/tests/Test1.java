package com.framework.tarun.tests;

import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Utils.BrowserFactory;
import Utils.Config;
import Utils.ExcelUtils;
import reports.ExtentManager;
import Utils.ReusableComponents;

public class Test1 {
    private static By createNewAccountBtn = By.xpath("//a[contains(text(),'Create new account')]");
    private static By firstName = By.xpath("//input[@name='firstname']");
    private static By lastName = By.xpath("//input[@name='lastname']");
    private static By month = By.xpath("//select[@id='month']");
    private static By day = By.xpath("//select[@id='day']");
    private static By year = By.xpath("//select[@id='year']");
    private static By gender = By.xpath("//label[text()='Male']");
    private static By email = By.xpath("//input[@name='reg_email__']");
    private static By password = By.xpath("//input[@name='reg_passwd__']");
    private static By signUpBtn = By.xpath("//button[contains(text(),'Sign Up')]");

    // Configuration properties
    Config config = new Config();
    String baseUrl = config.getProperty("baseUrl");
    String browser = config.getProperty("browser");
    String headLess = config.getProperty("headLess");
    WebDriver driver;
    ExtentTest test;
    ReusableComponents rc;

    @BeforeTest
    public void openBrowser() {
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException("Browser value is missing in the configuration file.");
        }
        driver = BrowserFactory.getDriver(browser, headLess);
        rc = new ReusableComponents(driver);
        if (driver == null) {
            throw new IllegalArgumentException("Failed to initialize the driver for browser: " + browser);
        }
    }

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        return ExcelUtils.readExcelData("Book1.xlsx", "Sheet1");
    }

    @Test(dataProvider = "excelData")
    public void testSignUp(Map<String, String> userData) throws Exception {
        signUp(userData);  // Pass the Excel data to signUp method
        System.out.println(userData);
    }

    public void signUp(Map<String, String> userData) throws Exception {
        test = ExtentManager.createTest("Url Launch");
        driver.manage().window().maximize();
        driver.get(baseUrl);
        ExtentManager.logWithScreenshot(driver, Status.PASS, "Launched URL Successfully");
        rc.pause(2);

        rc.findElementWithFluentWait(createNewAccountBtn).click();
        ExtentManager.logWithScreenshot(driver, Status.INFO, "...Creating Account...");

        // Fill in fields using values from the Excel data
        if (userData.get("First Name") != null) rc.findElementWithFluentWait(firstName).sendKeys(userData.get("First Name"));
        if (userData.get("Last Name") != null) rc.findElementWithFluentWait(lastName).sendKeys(userData.get("Last Name"));

        WebElement dayElement = driver.findElement(day);
        WebElement monthElement = driver.findElement(month);
        WebElement yearElement = driver.findElement(year);

        // Select Month, Day, Year based on Excel data
        if (userData.get("Month") != null) {
            Select selectMonth = new Select(monthElement);
            selectMonth.selectByVisibleText(userData.get("Month"));
        }

        if (userData.get("Day") != null) {
            Select selectDay = new Select(dayElement);
            selectDay.selectByVisibleText(userData.get("Day"));
        }

        if (userData.get("Year") != null) {
            Select selectYear = new Select(yearElement);
            selectYear.selectByVisibleText(userData.get("Year"));
        }

        rc.findElementWithFluentWait(gender).click();

        if (userData.get("Email") != null) rc.findElementWithFluentWait(email).sendKeys(userData.get("Email"));
        if (userData.get("Password") != null) rc.findElementWithFluentWait(password).sendKeys(userData.get("Password"));

        ExtentManager.logWithScreenshot(driver, Status.PASS, "Filled all the Data");
        rc.findElementWithFluentWait(signUpBtn).click();
        rc.pause(2);
    }


    @AfterTest
    public void quit() {
        test = ExtentManager.createTest("Quit Browser");
        if (driver != null) {
            driver.quit();
            test.log(Status.PASS, "Browser Closed Successfully");
        }
        ExtentManager.flushReports();
    }
}

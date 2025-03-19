package com.framework.tarun.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Utils.BrowserFactory;
import Utils.Config;
import reports.ExtentManager;
import Utils.ReusableComponents;

public class Test1{
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

	//configuration properties
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

	@Test
	public void signUp() throws Exception {
		test = ExtentManager.createTest("Url Launch");  // Assign a new test instance
		driver.manage().window().maximize();  
		driver.get(baseUrl);  
		ExtentManager.logWithScreenshot(driver, Status.PASS, "Launched URL Successfully");
		rc.pause(2);
		rc.findElementWithFluentWait(createNewAccountBtn).click();
		ExtentManager.logWithScreenshot(driver, Status.INFO, "...Creating Account...");
		rc.findElementWithFluentWait(firstName).sendKeys("Testuser");
		rc.findElementWithFluentWait(lastName).sendKeys("testsurname");
		WebElement dayElement = driver.findElement(day);
		WebElement monthElement = driver.findElement(month);
		WebElement yearElement = driver.findElement(year);
		Select selectMonth = new Select(monthElement);
		selectMonth.selectByVisibleText("Aug");
		Select selectDay = new Select(dayElement);
		selectDay.selectByVisibleText("12");
		Select selectYear = new Select(yearElement);
		selectYear.selectByVisibleText("2000");
		rc.findElementWithFluentWait(gender).click();
		rc.findElementWithFluentWait(email).sendKeys("sdetemail@yopmail.com");
		rc.findElementWithFluentWait(password).sendKeys("Test!2025");
		ExtentManager.logWithScreenshot(driver, Status.PASS, "Filled all the Data");
		rc.findElementWithFluentWait(signUpBtn).click();
		rc.pause(2);
	}

	@Test
	public void login() {
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

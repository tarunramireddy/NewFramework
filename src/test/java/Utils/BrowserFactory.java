package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	public static WebDriver getDriver(String browser, String headLess) {
		WebDriver driver = null;

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			if(headLess.equalsIgnoreCase("yes")) {
				// Set Chrome options for headless execution
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless"); // Run Edge in headless mode
				options.addArguments("--disable-gpu"); // Disable GPU, as it's unnecessary in headless mode
				options.addArguments("--no-sandbox"); // To avoid issues in CI environments
				driver = new ChromeDriver(options); // Pass the options to ChromeDriver
			} 
			else {
				driver = new ChromeDriver();
			}
		}
		else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();

			if(headLess.equalsIgnoreCase("yes")) {
				// Set Edge options for headless execution
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--headless"); // Run Edge in headless mode
				options.addArguments("--disable-gpu"); // Disable GPU, as it's unnecessary in headless mode
				options.addArguments("--no-sandbox"); // To avoid issues in CI environments
				driver = new EdgeDriver(options); // Pass the options to EdgeDriver
			}
			else{
				driver = new EdgeDriver();
			}
		}
		else {
			throw new IllegalArgumentException("Invalid Browser: " + browser + ". Supported browsers are: chrome, edge.");
		}

		return driver;
	}
}
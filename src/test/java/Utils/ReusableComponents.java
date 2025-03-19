package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.function.Function;

public class ReusableComponents {
    private WebDriver driver;

    public ReusableComponents(WebDriver driver) {
        this.driver = driver;
    }

    // Method with FluentWait
    public WebElement findElementWithFluentWait(By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        
        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
    }

    // Method with ExplicitWait
    public WebElement findElementWithExplicitWait(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Method with ImplicitWait
    public WebElement findElementWithImplicitWait(By locator) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver.findElement(locator);
    }
    
    //Thread.sleep
    public void pause(int seconds) {
    	try {
    		Thread.sleep(seconds*1000);
    	}
    	catch (InterruptedException  e) {
    		e.printStackTrace();
		}
    }
}


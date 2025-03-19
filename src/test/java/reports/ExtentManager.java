package reports;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            extent.attachReporter(new ExtentSparkReporter("ExtentReport.html"));
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = getInstance().createTest(testName);
        return test;
    }
    
    public static void logWithScreenshot(WebDriver driver, Status status, String message) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "screenshots/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
            File destFile = new File(path);
            FileUtils.copyFile(srcFile, destFile);
            test.log(status, message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (IOException e) {
            test.log(status, message + " (Screenshot capture failed)");
        }
    }


    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}

Selenium Java Framework Documentation
1. Introduction
This is a custom-built Selenium Java Framework designed to automate web application testing with enhanced reusability, maintainability, and scalability. The framework supports multiple browsers, handles test data via Excel (Apache POI), and provides detailed reporting using Extent Reports. It also allows easy configuration and test execution via Maven commands and TestNG.
Technologies & Libraries Used

- Selenium WebDriver: For browser automation.
- Apache POI: For handling Excel files (data-driven testing).
- Maven: For dependency management and build automation.
- TestNG: For test execution and reporting.
- Extent Reports: For generating customized HTML reports.
- Java SE-17: Compatible JDK version.

2. Project Structure

com.framework.tarun
│
├─── src/main/java
├─── src/main/resources
│
├─── src/test/java
│   └─── com.framework.tarun.tests
│       └── Test1.java       (Example Test Class using Data-Driven Testing)
│   ├─── reports
│       └── ExtentManager.java (Handles Extent Report creation and configuration)
│   ├─── Utils
│       ├── BrowserFactory.java     (Browser Initialization & Configuration)
│       ├── Config.java              (Reads configuration properties)
│       ├── ExcelUtils.java          (Handles Excel operations using Apache POI)
│       ├── ReusableComponents.java  (Provides reusable waits and utility methods)
│
├─── src/test/resources
│   └── config.properties     (Configuration file for browser & framework settings)
│
├─── screenshots              (Stores screenshots captured during tests)
├─── test-output              (Stores TestNG-generated reports)
├─── Book1.xlsx               (Excel file for data-driven testing)
├─── ExtentReport.html        (Generated HTML report)
├─── pom.xml                  (Maven Project Object Model file)
├─── testng.xml               (TestNG suite configuration file)

3. Features

3.1. Multi-Browser Testing
- Supports multiple browsers like Chrome, Microsoft Edge, etc.
- Easily configurable via the BrowserFactory class and config.properties file.
- Adding a new browser is as simple as modifying the BrowserFactory class and updating the configuration file.

3.2. Reusable Components
- Predefined methods for handling Explicit, Implicit, and Fluent waits.
- Available through ReusableComponents.java for use in any test without the need to declare them each time.

3.3. Test-Driven Development (TDD) Using Apache POI
- Supports data-driven testing by integrating TestNG Data Providers and Apache POI.
- Example implementation in Test1.java, demonstrating how data from Excel is read and executed for each row as a separate test case.

3.4. Customized Reporting with Extent Reports
- Generates detailed HTML reports using ExtentManager.java.
- Allows adding test titles, descriptions, screenshots, and statuses (Passed/Failed).
- Provides a structured and interactive report for better test analysis.

3.5. Command-Line Execution Using Maven
- Tests can be executed via command line using Maven commands:
  - mvn clean - Cleans the project by deleting the target directory.
  - mvn test - Runs the TestNG test suite as configured in testng.xml.
- Also supports traditional TestNG execution from the IDE.

4. Configuration

config.properties (Located in src/test/resources)
browser=chrome  # Specify browser type (e.g., chrome, edge)
url=https://example.com  # Application URL to test
implicitWait=10  # Implicit Wait Time (in seconds)
explicitWait=20  # Explicit Wait Time (in seconds)

5. Utilities

BrowserFactory.java
- Initializes the browser specified in the config.properties file.
- Can be easily extended to include new browsers by modifying the getBrowser() method.

Config.java
- Reads configuration properties from config.properties.
- Provides methods to access each property.

ExcelUtils.java
- Handles reading and writing of data from Excel files using Apache POI.
- Supports fetching data row-by-row for Data-Driven Testing.

ReusableComponents.java
- Contains reusable methods for:
  - Implicit Waits
  - Explicit Waits (WebDriverWait)
  - Fluent Waits (Customizable Waits)
- Provides a cleaner and more maintainable way to handle waits without repeating code.

6. Test Execution

Using TestNG
Tests are defined in the testng.xml file:

<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="MyTestSuite" parallel="false">
    <test name="MyTests">
        <classes>
            <class name="com.framework.tarun.tests.Test1"/>
        </classes>
    </test>
</suite>

Using Maven (Command-Line)
Clean the project: mvn clean
Execute tests: mvn test

7. Reporting

Reports are generated using Extent Reports and stored as ExtentReport.html in the root directory.
The ExtentManager.java file configures the report, including:
- Adding test titles, descriptions, screenshots, etc.
- Highlighting test status (Passed/Failed).

8. Test Data Handling (Excel)

Test data is stored in Book1.xlsx and accessed via ExcelUtils.java.

Example Usage in Test1.java:
@DataProvider(name = "ExcelData")
public Object[][] getData() {
    String filePath = "Book1.xlsx";
    String sheetName = "Sheet1";
    return ExcelUtils.getTestData(filePath, sheetName);
}

9. Best Practices

- Use ReusableComponents: Avoid declaring waits in each test class, just call the required methods from ReusableComponents.java.
- Centralized Browser Configuration: Keep all browser-related configurations in BrowserFactory.java and config.properties.
- Modular Approach: Keep utilities and test classes separated for better maintainability.

10. Future Enhancements

- Adding support for Parallel Test Execution.
- Integrating with CI/CD pipelines (e.g., Jenkins, GitHub Actions).
- Implementing API Testing Support.
- Enhancing reports with more visual representation.


package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CarbohydrateCalculatorTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.calculator.net/carbohydrate-calculator.html");
    }

    @Test(description = "Verify calculation works with valid metric inputs")
    public void verifyValidCalculation() {
        driver.findElement(By.id("cage")).clear();
        driver.findElement(By.id("cage")).sendKeys("26");

        driver.findElement(By.id("cheightmeter")).clear();
        driver.findElement(By.id("cheightmeter")).sendKeys("169");

        driver.findElement(By.id("ckg")).clear();
        driver.findElement(By.id("ckg")).sendKeys("65");

        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Result"), "Result not displayed");
    }

    @Test(description = "Verify system behavior for invalid age input")
    public void verifyInvalidAgeInput() {
        driver.findElement(By.id("cage")).clear();
        driver.findElement(By.id("cage")).sendKeys("abc");

        driver.findElement(By.id("cheightmeter")).clear();
        driver.findElement(By.id("cheightmeter")).sendKeys("170");

        driver.findElement(By.id("ckg")).clear();
        driver.findElement(By.id("ckg")).sendKeys("65");

        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        String pageSource = driver.getPageSource();
        Assert.assertFalse(pageSource.contains("Result"), "Calculation should not be performed for invalid age");
    }

    @Test(description = "Verify system behavior when fields are empty")
    public void verifyEmptyFieldsSubmission() {
        driver.findElement(By.id("cage")).clear();
        driver.findElement(By.id("cheightmeter")).clear();
        driver.findElement(By.id("ckg")).clear();

        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        String pageSource = driver.getPageSource();
        Assert.assertFalse(pageSource.contains("Result"),
                "Calculation should not be performed when fields are empty");
    }

    @Test(description = "Verify system behavior for unrealistic Weight input")
    public void verifyUnrealisticWeight() {
        driver.findElement(By.id("cage")).clear();
        driver.findElement(By.id("cage")).sendKeys("26");

        driver.findElement(By.id("cheightmeter")).clear();
        driver.findElement(By.id("cheightmeter")).sendKeys("180");

        driver.findElement(By.id("ckg")).clear();
        driver.findElement(By.id("ckg")).sendKeys("1000000");

        driver.findElement(By.xpath("//input[@value='Calculate']")).click();

        String pageSource = driver.getPageSource();
        Assert.assertFalse(pageSource.contains("Result"),
                "Calculation should not be performed for unrealistic Weight");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}

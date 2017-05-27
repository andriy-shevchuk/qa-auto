package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 20.05.2017.
 */
public class LoginTest {

    WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() {
        webDriver = new FirefoxDriver();
        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        webDriver.navigate().to("https://alerts.shotspotter.biz/");
    }

    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    @Test
    public void PositiveLoginTest() {

        WebElement emailField = webDriver.findElement(By.xpath("//input[@type='email']"));
        WebElement passwordField = webDriver.findElement(By.xpath("//input[@type='password']"));
        WebElement GoButton = webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']"));


        Assert.assertEquals(webDriver.getCurrentUrl(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");

        emailField.sendKeys("denvert1@shotspotter.net");
        passwordField.sendKeys("Test123!");
        GoButton.click();

        Assert.assertEquals(webDriver.getTitle(), "Shotspotter - Login", "Main page title is wrong");
        WebElement settingsItem = webDriver.findElement(By.className("settings"));
        Assert.assertTrue(settingsItem.isDisplayed(), "settings icon is not displayed");
        Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"),"Wrong url after Login");

        //Assert.assertEquals(webDriver.getTitle(), "")
//        SoftAssert assertion = new SoftAssert();
//
//
//        assertion.assertTrue(webDriver.findElement(By.xpath("//*[@class='settings']")).isDisplayed());
//        assertion.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"));
//
//
//        assertion.assertAll();

    }

//    @Test
//    public void NegativeLoginTest1() {
//        WebDriver webDriver = new FirefoxDriver();
//        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
//        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
//        webDriver.navigate().to("https://alerts.shotspotter.biz/");
//
//        webDriver.findElement(By.xpath("//input[@type='email']")).sendKeys("incorrectEmail");
//        webDriver.findElement(By.xpath("//input[@type='password']")).sendKeys("IncorrectPassword");
//        webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']")).click();
//
//        SoftAssert assertion = new SoftAssert();
//        assertion.assertTrue(webDriver.findElement(By.xpath("//*[@class='invalid-credentials']")).isDisplayed());
//        webDriver.quit();
//        assertion.assertAll();
//
//    }

}

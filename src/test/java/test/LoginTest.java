package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;

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

        LoginPage loginPage = new LoginPage(webDriver);

        Assert.assertEquals(webDriver.getCurrentUrl(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");

        loginPage.LoginBy("denvert1@shotspotter.net","Test123!");
        //loginPage.typeEmail("denvert1@shotspotter.net");
        //loginPage.typePassword("Test123!");
        loginPage.clickGoButton();

        Assert.assertEquals(webDriver.getTitle(), "Shotspotter - Login", "Main page title is wrong");
        WebElement settingsItem = webDriver.findElement(By.className("settings"));
        Assert.assertTrue(settingsItem.isDisplayed(), "settings icon is not displayed");
        Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"),"Wrong url after Login");

    }

    @Test
    public void NegativeLoginTest1() {

        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.typeEmail("incorrectEmail");
        loginPage.typePassword("IncorrectPassword");
        loginPage.clickGoButton();

        try {
            Assert.assertTrue(webDriver.findElement(By.xpath("//*[@class='invalid-credentials']")).isDisplayed());
        } catch (Exception e) {
            throw new AssertionError("no invalid credentials element displayed");
        }

    }

}

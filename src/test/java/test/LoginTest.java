package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;

/**
 * Created by Admin on 20.05.2017.
 */
public class LoginTest {

    WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() {
        webDriver = new FirefoxDriver();


    }

    @AfterMethod
    public void afterMethod() {

        webDriver.quit();
    }

    @Test
    public void PositiveLoginTest() {

        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        Assert.assertEquals(webDriver.getCurrentUrl(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");
        Assert.assertEquals(webDriver.getTitle(), "ShotSpotter - Login", "Main page title is wrong");

        MainPage mainPage = loginPage.LoginBy("denvert1@shotspotter.net","Test123!");

        Assert.assertTrue(mainPage.isPageLoaded(), "settings icon is not displayed");
        Assert.assertTrue(webDriver.getCurrentUrl().contains("https://alerts.shotspotter.biz/main"),"Wrong url after Login");

    }

    @Test
    public void NegativeLoginTest1() {

        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        loginPage.IncorrectLogin("IncorrectEmail", "IncorrectPassword");

        Assert.assertTrue(loginPage.IsInvalidCredentialsDisplayed());

    }

}

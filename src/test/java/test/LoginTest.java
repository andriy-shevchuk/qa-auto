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

        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");
        Assert.assertEquals(loginPage.getPageTitle(), "ShotSpotter - Login", "Main page title is wrong");

        MainPage mainPage = loginPage.login("denvert1@shotspotter.net","Test123!", MainPage.class);

        Assert.assertTrue(mainPage.isPageLoaded(), "settings icon is not displayed");
        Assert.assertTrue(mainPage.getPageURL().contains("https://alerts.shotspotter.biz/main"),"Wrong url after Login");

    }

    @Test
    public void NegativeLoginTest1() {

        String expectedErrorMsg = "The provided credentials are not correct.";

        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        LoginPage resultPage = loginPage.login("IncorrectEmail", "IncorrectPassword", LoginPage.class);
        Assert.assertTrue(resultPage.IsInvalidCredentialsDisplayed(), "Invalid credentials is not displayed");
        Assert.assertEquals(loginPage.getErrorText(), expectedErrorMsg, "Error text is wrong");
        Assert.assertTrue(resultPage.isLoginPageLoaded(), "Login page is not loaded");

    }

}

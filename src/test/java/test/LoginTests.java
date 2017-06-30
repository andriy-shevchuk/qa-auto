package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;

/**
 * Test class
 *
 * Created by Admin on 20.05.2017.
 */
public class LoginTests {

    /**
     * Local Webdriver variable
     */
    WebDriver webDriver;

    /**
     * Initialises FirefoxDriver and navigates to LoginPage
     */
    @BeforeMethod
    public void beforeMethod() {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("https://alerts.shotspotter.biz/");
    }

    /**
     * Closes WebDriver instance
     */
    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    /**
     * Defines default username to login
     */
    public String username = "sst.tau@gmail.com";

    /**
     * Defines password for default user
     */
    public String password = "P@ssword123";



    /**
     * Simple Positive Login Test
     */
    @Test
    public void PositiveLoginTest() {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");

        MainPage mainPage = loginPage.login(username, password);
        Assert.assertTrue(mainPage.isPageLoaded(), "settings icon is not displayed");
        Assert.assertTrue(mainPage.getPageURL().contains("https://alerts.shotspotter.biz/main"), "Wrong url after Login");
    }


    @DataProvider
    public static Object[][] NegativeLOginTestProvider() {
        return new Object[][] {
                {"IncorrectLogin", "IncorrectPassword", "The provided credentials are not correct."},
                {"sst.tau@gmail.com", "IncorrectPassword", "The provided credentials are not correct."},
                {"", "", "The provided credentials are not correct."}
        };
    }


    /**
     * Simple Negative Login test
     */
    @Test (dataProvider = "NegativeLOginTestProvider")
    public void NegativeLoginTest1(String email, String password, String invalidCredentialsText) {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");

        loginPage = loginPage.login(email, password);
        Assert.assertTrue(loginPage.IsInvalidCredentialsDisplayed(), "Invalid credentials is not displayed");
        Assert.assertEquals(loginPage.getErrorText(), invalidCredentialsText, "Error text is wrong");
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        loginPage.clearCredentials();
    }

    /**
     * Logout from MainPage test
     */
    @Test
    public void TestLogout() {
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
        Assert.assertEquals(loginPage.getPageURL(), "https://alerts.shotspotter.biz/", "Wrong url on Login Page");
        Assert.assertEquals(loginPage.getPageTitle(), "Shotspotter - Login", "Main page title is wrong");

        MainPage mainPage = loginPage.login(username, password);
        Assert.assertTrue(mainPage.isPageLoaded(), "settings icon is not displayed");
        Assert.assertTrue(mainPage.getPageURL().contains("https://alerts.shotspotter.biz/main"), "Wrong url after Login");

        loginPage = mainPage.logout();
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded");
    }
}

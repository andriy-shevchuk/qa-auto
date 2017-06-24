package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;

/**
 * Created by Admin on 20.06.2017.
 */
public class MainPageTests {

    /**
     * Local Webdriver variable
     */
    WebDriver webDriver;
    MainPage mainPage;

    /**
     * Initialises FirefoxDriver and navigates to LoginPage
     */
    @BeforeClass
    public void beforeClass() {
        webDriver = new FirefoxDriver();
        webDriver.navigate().to("https://alerts.shotspotter.biz/");
        LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);
        loginPage.isPageLoaded();
        mainPage = loginPage.login(username, password);
    }

    /**
     * Closes WebDriver instance
     */
    @AfterClass
    public void afterClass() {
        webDriver.quit();
    }
    /**
     * Defines default username to login
     */
    public String username = "denvert1@shotspotter.net";

    /**
     * Defines password for default user
     */
    public String password = "Test123!";

    /**
     * Test Incidents Period Switch and checks quantity of Incident Cards
     */
    @Test
    public void testIncidentsPeriodSwitch() {

        int[] timeFrameOtions = {24, 3, 7};

        for (int timeFrameOption : timeFrameOtions) {
            mainPage.switchTimeFramePeriod(timeFrameOption);
            int resultsCount = mainPage.getResultsCount();
            int IncidentCardsCount = mainPage.getIncidentCardsCount();
            Assert.assertEquals(resultsCount, IncidentCardsCount, "Results count does not match incidentCardsCount");
        }


    }

    @DataProvider
    public static Object[][] timeFrameOptions() {
        return new Object[][] {{24},{3},{7}};
    }

    /**
     * Test Incidents Period Switch and checks quantity of Incident Cards
     */
    @Test (dataProvider = "timeFrameOptions")
    public void testIncidentsPeriodSwitchByDataProvider(int timeFrameOption) {


        mainPage.switchTimeFramePeriod(timeFrameOption);
        int resultsCount = mainPage.getResultsCount();
        int IncidentCardsCount = mainPage.getIncidentCardsCount();
        Assert.assertEquals(resultsCount, IncidentCardsCount, "Results count does not match incidentCardsCount");

    }
}

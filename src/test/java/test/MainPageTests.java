package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import page.LoginPage;
import page.MainPage;
import page.TermsOfServicePage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;


/**
 * Created by Admin on 20.06.2017.
 */
public class MainPageTests extends BaseTest {

    /**
     * Local Webdriver variable
     */
    WebDriver webDriver;
    MainPage mainPage;

    /**
     * Initialises FirefoxDriver and navigates to LoginPage
     */
    @Parameters ({ "browserName" })
    @BeforeClass
    public void beforeClass(@Optional ("firefox") String browserName) {
        webDriver = StartBrowser(browserName);
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

    /**
     * Test Incidents Period Switch and checks quantity of Incident Cards
     */
    @Test
    public void testIncidentsDetails() {
        Assert.assertFalse(mainPage.isAddressesListContainsEmptyStrings(), "Addresses list contains empty strings" );
        Assert.assertTrue(mainPage.isAllCitiesEqualsTo("Denver"), "Not all cities are Denver" );
        Assert.assertTrue(mainPage.isTimeListContainsUniqueElements(), "Elements of timeList are not unique" );
    }

    @Test
    public void testInsidentsDetails() {
        String expectedCity = "Denver";
        mainPage.switchTimeFramePeriod(7);
        mainPage.openIncidentsList();

        List<String> listCities = mainPage.getIncidentCardsDetails("Cities");
        List<String> listStreets = mainPage.getIncidentCardsDetails("Streets");
        List<String> listTimeStamps = mainPage.getIncidentCardsDetails("TimeStamps");

        for (String elementCity: listCities) {
            //Assert.assertEquals(expectedCity, elementCity, "City is not Denver");
        }

        for (String elementStreet: listStreets) {
            Assert.assertNotEquals(elementStreet, "", "Street address is empty");
        }

        for (String elementTimeStamp: listTimeStamps) {
            Assert.assertNotEquals(elementTimeStamp, "", "TimeStamp is empty");
        }

    }

    @Test
    public void TermsOfServiceOpenTest() {

        String firstTab = webDriver.getWindowHandle();

        TermsOfServicePage termsOfServicePage = mainPage.goToTermsOfServicePage();

        for(String winHandle : webDriver.getWindowHandles()){
            webDriver.switchTo().window(winHandle);
        }

        termsOfServicePage.isPageLoaded();

        Assert.assertEquals(termsOfServicePage.getPageTitle(), "Apps-TOS", "Page title does not match");
        Assert.assertEquals(termsOfServicePage.getPageURL(), "http://www.shotspotter.com/apps/tos", "Page url does not match");

        webDriver.close();
        webDriver.switchTo().window(firstTab);
        mainPage.closePopup();

    }

    public String generateStringFromResource(String path) throws IOException {

        return new String(Files.readAllBytes(Paths.get(path)));

    }

    @Test
    public void NewAlertsNotificationTest() throws IOException {

        String jsonBody = generateStringFromResource("C:\\Users\\Demonologist\\Documents\\JavaProjects\\shotspotter\\qa-auto\\src\\test\\resources\\notifiactionReguestBody.json");

        given().
                contentType("application/json").
                body(jsonBody).
                when().
                post("https://alerts.shotspotter.biz/api/incidents/v2/").
                then().
                statusCode(200).
                body(containsString("incident message is published to queue successfully"));

        mainPage.switchTimeFramePeriod(7);

        Assert.assertTrue(mainPage.ifCardsListContainsStreet("TestStreetAndriy4"),"Card list does not contain desired street");

    }




}

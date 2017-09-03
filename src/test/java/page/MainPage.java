package page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;



/**
 * PageObject MainPage class
 *
 * Created by Admin on 30.05.2017.
 */
public class MainPage extends BasePage {
    /**
     * Defines settingsItem element
     */
    @FindBy(className = "settings")
    private WebElement settingsItem;

    /**
     * Defines logoutElement element
     */
    @FindBy(xpath = "//li[text() = 'Logout']")
    private WebElement logoutElement;

    /**
     * Defines aboutElement element
     */
    @FindBy(xpath = "//li[text() = 'About']")
    private WebElement aboutElement;


    /**
     * Defines settingsMenu element
     */
    @FindBy(xpath = "//div[@class='settings isOpen']")
    private WebElement settingsMenu;

    @FindBy(xpath = "//filter-menu/div[@class='selected-option']")
    private WebElement incidentsTimeFrameSwitch;

    @FindBy(xpath = "//div[@class='available-options']")
    private WebElement optionsList;

    @FindBy(xpath = "//*[@class='result-count']")
    private WebElement resultsCount;

    @FindBy(xpath = "//div[text()='List']")
    private  WebElement listButton;

    @FindBy(xpath = "//incident-list//incident-card")
    private List<WebElement> incidentsCardsList;

    @FindBy(xpath = "//div[@class='selected-option']/span[@class='time-increment']")
    private WebElement currentTimeFrameValue;

    @FindBy(xpath = "//div[@class='incidents']")
    private WebElement incidentsPanel;

    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='address']")
    private List<WebElement> addressesList;

    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='city S']")
    private List<WebElement> citiesList;

    @FindBy(xpath = "//incident-list//incident-card//div[contains(@class, 'incident')]//div[@class='cell day']//div[@class='content']")
    private List<WebElement> timeList;

    @FindBy(xpath = "//a[text()='terms of service']")
    private WebElement termsOfServiceLink;

    @FindBy(xpath = "//button[contains(text(), 'Close')]")
    private WebElement closeButton;



    /**
     * Finds WebElement according to selected timeIncrementValue
     *
     * @param timeIncrementValue integer which timeFrame to choose
     * @return WebElement of chosen timeIncrement
     */
    private WebElement getTimeFramePeriodOption(int timeIncrementValue) {
        String xpath = String.format("//filter-menu//div[@class='available-options']//span[text()='%d']", timeIncrementValue);
        WebElement timeFrameSwitch = webDriver.findElement(By.xpath(xpath));
        return timeFrameSwitch;
    }

    /**
     * MainPage constructor
     *
     * @param driver WebDriver instance
     */
    public MainPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Checks if MainPage is loaded
     *
     * @return True if MainPage is loaded, False if not
     */
    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(settingsItem, 15).isDisplayed();
    }

    /**
     * Gets number of spotted results
     *
     * @return integer quantity of spotted results
     */
    public int getResultsCount() {
        return Integer.parseInt(resultsCount.getText().replace(" RESULTS",""));
    }

    /**
     * Method to logout from MainPage
     *
     * @return LoginPage if logout successful
     */
    public LoginPage logout() {
        settingsItem.click();
        waitUntilElementDisplayed(settingsMenu);
        waitUntilElementDisplayed(logoutElement).click();
        return PageFactory.initElements(webDriver, LoginPage.class);
    }

    /**
     * Switches between time values
     *
     * @param timeIncrementValue integer which timeFrame to choose
     */
    public void switchTimeFramePeriod(int timeIncrementValue) {
        int selectedTimeFrameValue = Integer.parseInt(currentTimeFrameValue.getText());
        incidentsTimeFrameSwitch.click();
        waitUntilElementDisplayed(optionsList);
        waitUntilElementDisplayed(getTimeFramePeriodOption(timeIncrementValue)).click();
        waitForResultsCountRefresh(selectedTimeFrameValue, timeIncrementValue);
    }


    /**
     * Waits of proper resultsCount
     *
     * @param selectedTimeFrameValue integer current timeFrame value
     * @param timeIncrementValue integer chosen timeIncrementValue
     */
    public void waitForResultsCountRefresh(int selectedTimeFrameValue, int timeIncrementValue) {
        if (selectedTimeFrameValue != timeIncrementValue) {
            int currentTimeFrameValue = getResultsCount();
            long end = System.currentTimeMillis() + 15*1000; // 15 seconds * 1000 ms/sec
            while (System.currentTimeMillis() < end) {
                if (currentTimeFrameValue != getResultsCount()) {
                    break;
                }
            }
        }
    }

    /**
     * Counts quantity of IncidentCards
     *
     * @return int quantity of incident cards
     */
    public int getIncidentCardsCount() {
        listButton.click();
        waitUntilElementDisplayed(incidentsPanel);
        return incidentsCardsList.size();
    }

    /**
     * Checks if Addresses list contains empty Strings
     *
     * @return true if contains, false if not
     */
    public boolean isAddressesListContainsEmptyStrings() {
         for (WebElement address : addressesList) {
           if (address.getText().equalsIgnoreCase("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all Cities in Cities list are equal to string
     *
     * @param cityText String city to check
     * @return true if all cities are cityText, false if not
     */
    public boolean isAllCitiesEqualsTo(String cityText) {
        for (WebElement city : citiesList) {
            if (!city.getText().equalsIgnoreCase(cityText)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if Time list contains unique elements
     *
     * @return true if Time list contains unique elements
     */
    public boolean isTimeListContainsUniqueElements() {

       List<String> timeTextList = new ArrayList<>();

       for (WebElement timeElement : timeList) {
           timeTextList.add(timeElement.getText());
       }

       Set<String> timeTextUnique = new HashSet<String>(timeTextList);

       if (timeTextUnique.size() == timeTextList.size()) {
           return true;
       } else {
           return false;
       }

    }

    /**
     * Opens incidents list
     */
    public void openIncidentsList() {
        listButton.click();
        waitUntilElementDisplayed(incidentsCardsList.get(1), 5);
    }

    /**
     * Gets list of Cities from Incidents cards list
     *
     * @return listCities List
     */
    public List<String> getIncidentCardsCities() {
        List<String> listCities = new ArrayList<String>();
        for (WebElement incidentCard: incidentsCardsList) {
            String cityText = incidentCard.findElement(By.xpath("//div[@class='city S']")).getText();
            listCities.add(cityText);
        }
        return listCities;
    }

    /**
     * Gets List of Streets from Incidents cards list
     *
     * @return listStreets List
     */
    public List<String> getIncidentCardsStreets() {
        List<String> listStreets = new ArrayList<String>();
        for (WebElement incidentCard: incidentsCardsList) {
            String streetText = incidentCard.findElement(By.xpath("//div[@class='address']")).getText();
            listStreets.add(streetText);
        }
        return listStreets;
    }

    /**
     * Gets List of TimeStamps from Incidents cards list
     *
     * @return listTimeStamp List
     */
    public List<String> getIncidentCardsTimeStamps() {
        List<String> listTimeStamp = new ArrayList<String>();
        for (WebElement incidentCard: incidentsCardsList) {
            String timeStampText = incidentCard.findElement(By.xpath("//div[@class='cell day']//div[@class='content']")).getText();
            listTimeStamp.add(timeStampText);
        }
        return listTimeStamp;
    }

    /**
     * Gets List of desired Incident cards details elements
     *
     * @param details String "cities", "streets", "timestamps"
     * @return listDetails List of WebElements
     */
    public List<String> getIncidentCardsDetails(String details) {
        List<String> listDetails = new ArrayList<>();
        String elementXpath = getCardsDetailsElementXpath(details);
        for (WebElement cardsElement: incidentsCardsList) {
            String cardsElementText = cardsElement.findElement(By.xpath(elementXpath)).getText();
            listDetails.add(cardsElementText);
        }
        return listDetails;
    }

    /**
     * Gets Xpath String to find Incident cards details List of WebElements
     *
     * @param details String "cities", "streets", "timestamps"
     * @return String with xPath of Incident cards Details elements cities, streets, timestamps
     */
    public String getCardsDetailsElementXpath(String details) {
        switch (details.toLowerCase()) {
            case "cities":
                return ".//div[@class='city S']";
            case "streets":
                return ".//div[@class='address']";
            case "timestamps":
                return ".//div[@class='cell day']//div[@class='content']";
            default:
                return "";
        }
    }

    /**
     * Opens settings menu, clicks About, clicks Terms of Srevice Link
     *
     * @return TermsOfService PageObject
     */
    public TermsOfServicePage goToTermsOfServicePage() {
        settingsItem.click();
        waitUntilElementDisplayed(settingsMenu);
        aboutElement.click();
        waitUntilElementDisplayed(termsOfServiceLink).click();
        return PageFactory.initElements(webDriver, TermsOfServicePage.class);
    }

    /**
     * Closes Popup window on the MainPage
     */
    public void closePopup() {
        closeButton.click();
    }


    public boolean ifCardsListContainsStreet(String street) {

        List<String> listStreets = getIncidentCardsDetails("Streets");
            for (String elementStreet: listStreets) {
                System.out.println(street);
            if (elementStreet.contains(street)) {
                return true;
                }
        }
        return false;
    }
}


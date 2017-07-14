package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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

    //addresses list
    //incident-list//incident-card//div[@class='incident']//div[@class='address']
    // also find city, and check if it is Denver, check time differs from each other


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
}


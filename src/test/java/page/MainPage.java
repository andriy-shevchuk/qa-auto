package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
}


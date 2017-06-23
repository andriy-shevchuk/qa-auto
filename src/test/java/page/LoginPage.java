package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * PageObject LoginPage class
 *
 * Created by Admin on 27.05.2017.
 */
public class LoginPage extends BasePage {

    /**
     * Defines emailField WebElement
     */
    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;

    /**
     * Defines passwordFeield element
     */
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    /**
     * Defines goButton element
     */
    @FindBy(xpath = "//*[@class='button' and text()='GO']")
    private WebElement goButton;

    /**
     * Defines invalidCredentials element
     */
    @FindBy(className = "invalid-credentials")
    WebElement invalidCredentials;

    /**
     * LoginPage constructor
     *
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Common method to login into mainpage
     *
     * @param email    String user e-mail
     * @param password String user password
     * @return PageObject MainPage if login successful, LoginPage if not
     */
    public <T> T login(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
        if (isElementDisplayed(goButton, 3)) {
            return (T) this;
        } else {
            return (T) PageFactory.initElements(webDriver, MainPage.class);
        }
    }

    /**
     * Checks if InvalidCredentials element displayed
     *
     * @return True if element is displayed, False if not
     */
    public boolean IsInvalidCredentialsDisplayed() {
        return waitUntilElementDisplayed(invalidCredentials).isDisplayed();
    }

    /**
     * Gets text of InvalidCredentials element
     *
     * @return String text of InvalidCredentials element
     */
    public String getErrorText() {
        return waitUntilElementDisplayed(invalidCredentials).getText();
    }

    /**
     * Checks if LoginPage is loaded
     *
     * @return True if LoginPage is loaded, False if not
     */
    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(emailField).isDisplayed();
    }
}

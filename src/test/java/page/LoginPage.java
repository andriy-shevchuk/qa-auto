package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Admin on 27.05.2017.
 */
public class LoginPage extends BasePage{

    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@class='button' and text()='GO']")
    private WebElement goButton;

    @FindBy(className = "invalid-credentials")
    WebElement invalidCredentials;

    public LoginPage (WebDriver driver) {
        super(driver);
    }

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

    public boolean IsInvalidCredentialsDisplayed() {
        return waitUntilElementDisplayed(invalidCredentials).isDisplayed();
    }

    public String getErrorText() {
        return waitUntilElementDisplayed(invalidCredentials).getText();
    }

    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(emailField).isDisplayed();
    }
}

package page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

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
        driver.navigate().to("https://alerts.shotspotter.biz/");
        PageFactory.initElements(driver, this);

    }

    public MainPage LoginBy(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
        return new MainPage(webDriver);
    }

    public LoginPage IncorrectLogin(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
        return this;
    }

    public boolean IsInvalidCredentialsDisplayed() {
        return invalidCredentials.isDisplayed();
    }

    public String getErrorText() {
        return invalidCredentials.getText();
    }

    public boolean isLoginPageLoaded() {
        return emailField.isDisplayed();
    }


}

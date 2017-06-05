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
        driver.navigate().to("https://alerts.shotspotter.biz/");
    }

    public <T> T login(String email, String password, Class <T> expectedPage) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
        if (expectedPage == LoginPage.class) {
            return (T) this;
        } else {
            return PageFactory.initElements(webDriver, expectedPage);
        }
    }

    public boolean IsInvalidCredentialsDisplayed() {
        return waitUntilElementDisplayed(invalidCredentials, 15).isDisplayed();
    }

    public String getErrorText() {
        return waitUntilElementDisplayed(invalidCredentials, 15).getText();
    }

    public boolean isLoginPageLoaded() {
        return waitUntilElementDisplayed(emailField, 15).isDisplayed();
    }


}

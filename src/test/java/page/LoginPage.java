package page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 27.05.2017.
 */
public class LoginPage {

    WebDriver driver;

    @FindBy(how = How.XPATH, using = "//input[@type='email']")
    WebElement emailField;

    @FindBy(how = How.XPATH, using = "//input[@type='password']")
    WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//*[@class='button' and text()='GO']")
    WebElement goButton;

    @FindBy(how = How.XPATH, using = "//*[@class='invalid-credentials']")
    WebElement invalidCredentials;

    public LoginPage (WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.navigate().to("https://alerts.shotspotter.biz/");
    }

    public MainPage LoginBy(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        return mainPage;
    }

    public void IncorrectLogin(String email, String password) {
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        goButton.click();
    }

    public boolean IsInvalidCredentialsDisplayed() {
        return invalidCredentials.isDisplayed();
    }

}

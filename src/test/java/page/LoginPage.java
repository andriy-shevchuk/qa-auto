package page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Admin on 27.05.2017.
 */
public class LoginPage {

    WebDriver driver;

    By emailField = By.xpath("//input[@type='email']");
    By passwordField = By.xpath("//input[@type='password']");
    By goButton = By.xpath("//*[@class='button' and text()='GO']");

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    public void typeEmail(String email) {
        driver.findElement(emailField).sendKeys(email);

    }

    public void typePassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickGoButton() {
        driver.findElement(goButton).click();

    }



}

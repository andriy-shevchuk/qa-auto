package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * Created by Admin on 30.05.2017.
 */
public class MainPage extends BasePage {
    @FindBy(className = "settings")
    private WebElement settingsItem;

    @FindBy(xpath = "//settings-drop-down//li[text() = 'Logout']")
    private WebElement logoutElement;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(settingsItem, 15).isDisplayed();
    }

    public LoginPage logout() {
        settingsItem.click();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", logoutElement);
        //waitUntilElementDisplayed(logoutElement).click();
        return PageFactory.initElements(webDriver, LoginPage.class);
    }
}


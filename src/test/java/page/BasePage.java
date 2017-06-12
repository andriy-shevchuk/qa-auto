package page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Admin on 03.06.2017.
 */
public class BasePage {

    public WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getPageURL() {
        return webDriver.getCurrentUrl();
    }

    public String getPageTitle() {
        return webDriver.getTitle();
    }

    public WebElement waitUntilElementDisplayed(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        //wait.until(ExpectedConditions.elementToBeClickable(element));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitUntilElementDisplayed(WebElement element) {
        return waitUntilElementDisplayed(element, 15);
    }

    public boolean isElementDisplayed(WebElement element, int timeout) {
        try {
            waitUntilElementDisplayed(element, timeout).isDisplayed();
        }
        catch (TimeoutException e) {
            return false;
        }

        return true;
    }
}

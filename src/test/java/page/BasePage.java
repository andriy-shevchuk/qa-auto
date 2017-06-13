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

    /**
     * Common method to get current Page title
     *
     * @return String with current Page title
     */
    public String getPageTitle() {
        return webDriver.getTitle();
    }

    /**
     * Waits until element is displayed using specific max timeout
     *
     * @param element WebElement to wait for
     * @param timeout max timeout in seconds
     * @return WebElement after expected condition
     */
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

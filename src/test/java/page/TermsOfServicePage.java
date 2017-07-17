package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * PageObject MainPage class
 *
 * Created by Admin on 30.05.2017.
 */
public class TermsOfServicePage extends BasePage {

    @FindBy(xpath = "//b[contains(text(), 'Questions')]")
    private WebElement questionsElement;

    /**
     * TermsOfServicePage constructor
     *
     * @param driver WebDriver instance
     */
    public TermsOfServicePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(questionsElement, 15).isDisplayed();
    }

}


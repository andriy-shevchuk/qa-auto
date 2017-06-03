package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 30.05.2017.
 */
public class MainPage extends BasePage{


    @FindBy(className = "settings")
    private WebElement settingsItem;


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public boolean isPageLoaded() {
        return settingsItem.isDisplayed();
    }


}

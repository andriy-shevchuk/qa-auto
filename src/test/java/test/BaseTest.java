package test;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Created by Admin on 20.06.2017.
 */
public class BaseTest {

    public static WebDriver StartBrowser (String browserName) {


        switch (browserName.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver.exe");
                return new FirefoxDriver();
            case "chrome":
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver();

            default:
                return null;
        }

    }


}

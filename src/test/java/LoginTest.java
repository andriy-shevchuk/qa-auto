import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.lang.Thread.sleep;

/**
 * Created by Admin on 20.05.2017.
 */
public class LoginTest {

    public static void main(String[] args) {

        WebDriver webDriver = new FirefoxDriver();
        webDriver.navigate().to("https://alerts.shotspotter.biz/");
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.findElement(By.xpath("//input[@type='email']")).sendKeys("denvert1@shotspotter.net");
        webDriver.findElement(By.xpath("//input[@type='password']")).sendKeys("Test123!");
        webDriver.findElement(By.xpath("//*[@class='button' and text()='GO']")).click();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webDriver.quit();
    }

}

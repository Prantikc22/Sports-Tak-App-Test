package utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class BrowserFactory {

    /*Starts the application as per the driver, browser name and url. The last 2 items are stored in config.*/
    public static WebDriver startApplication(WebDriver driver, String browserName, String appURL)
    {
        switch (browserName) {
            case "Chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "Firefox" -> {
                WebDriverManager.chromedriver().setup();
                driver = new FirefoxDriver();
            }
            case "Edge" -> {
                WebDriverManager.chromedriver().setup();
                driver = new EdgeDriver();
            }
        }
        driver.manage().window().maximize();
        driver.get(appURL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }

    public static void quitBrowser(WebDriver driver)
    {
        driver.quit();
    }
}

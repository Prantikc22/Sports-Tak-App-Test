package utility;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Helper {

    WebDriver driver;
    String url = "";
    HttpURLConnection connection = null;
    int respCode = 200;

    public Helper(WebDriver driver){
        this.driver = driver;
    }

    /*This method captures the screenshot stores it in byte form because of in Cucumber-Extent Report
    * framework, screenshots are embedded into the report via this format. This method is called
    * after every failed scenario*/
    public static byte[] captureScreenshot(WebDriver driver){
        byte[] screenshot = null;
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            screenshot = ts.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenshot;
    }

    /*This mimics the click on an element with an <a> tag. In this method, a http request is sent and it is checked
    * whether the response code is valid or not, which in turn makes the link valid.*/
    public void verifyLink(List<WebElement> links, ConfigDataProvider config) {
        for (WebElement link : links) {
            url = link.getAttribute("href");
            System.out.println(url);
            if (url == null || url.isEmpty()) {
                System.out.println("URL is not configured properly for anchor tag");
                continue;
            }
            /*If the url has a different pattern than sports tak url, then ignore such urls.*/
            if (!url.startsWith(config.getUrl())) {
                System.out.println("URL does not belong to SportsTak. Hence ignoring it.");
                continue;
            }
            try {
                connection = (HttpURLConnection) (new URL(url).openConnection());
                connection.setRequestMethod("HEAD");
                connection.connect();
                respCode = connection.getResponseCode();
                if (respCode >= 400) {
                    System.out.println(url + " is a broken link");
                } else {
                    System.out.println(url + " is a valid link");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*Used to check the presence of an element. This replaces the need of any try catch block.*/
    public boolean isElementPresent(List<WebElement> elements) {
        return elements.size() > 0;
    }

}

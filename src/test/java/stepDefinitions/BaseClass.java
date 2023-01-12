package stepDefinitions;

import org.openqa.selenium.WebDriver;
import utility.BrowserFactory;
import utility.ConfigDataProvider;

public class BaseClass {

    public WebDriver driver;
    public ConfigDataProvider config;

    /*Initialization of variables*/
    public void setup() {
        config = new ConfigDataProvider();
    }

    /*Launch browser for automated test with a pre-configured browser and url in the config file.*/
    public void startApp(){
        driver = BrowserFactory.startApplication(driver, config.getBrowser(), config.getUrl());
    }
}

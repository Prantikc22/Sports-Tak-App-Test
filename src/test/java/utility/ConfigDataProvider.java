package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/*This class is used to fetch static data from config.properties file*/
public class ConfigDataProvider {

    Properties prop;

    /*Loads the config.properties file. The methods below are used to fetch the value as per the key stored in that file.*/
    public ConfigDataProvider() {
        File src = new File("config/config.properties");
        try {
            FileInputStream fis = new FileInputStream(src);
            prop = new Properties();
            prop.load(fis);
        } catch (Exception e){
            System.out.println("Not able to find the config file "+e.getMessage());
        }
    }

    public String getBrowser() {
        return prop.getProperty("Browser");
    }

    public String getUrl() {
        return prop.getProperty("appURL");
    }

    public String getHindiUrl() {
        return prop.getProperty("appURLHindi");
    }

    public String getDarkThemeColors() {
        return prop.getProperty("darkTheme");
    }

    public String getLightThemeColors() {
        return prop.getProperty("lightTheme");
    }
}

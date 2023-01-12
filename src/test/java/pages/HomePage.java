package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    @CacheLookup
    @FindBy(xpath = "//button[contains(@class, 'btnLangToggle')]")
    WebElement languageSelectionButton;

    @CacheLookup
    @FindBy(xpath = "//button/span[text()='Cricket']")
    WebElement cricketButton;

    @CacheLookup
    @FindBy(xpath = "//button[contains(@class, 'btnThemeToggle')]")
    WebElement themeButton;

    @CacheLookup
    @FindBy(xpath = "//h2[contains(text(),'Editor')]")
    WebElement editorText;

    @CacheLookup
    @FindBy(xpath = "//h2[text()='Webstories']")
    WebElement webStoriesText;


    public void clickOnLanguageButton() {
        languageSelectionButton.click();
    }

    public void clickOnCricketButton() {
        cricketButton.click();
    }

    public void clickOnThemeButton() {
        themeButton.click();
    }

    /*As there is a nested scroll bar, the editor text is clicked to get control of the main area. Then the scrolling
    * is performed till the web stories are visible in view. Then we return a list of total web stories.*/
    public List<WebElement> getListOfWebStories() {
        editorText.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",webStoriesText);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return driver.findElements(By.xpath("//div[@class='webstorieshome-cards']/a"));
    }
}

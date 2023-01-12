package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.HomePage;
import utility.BrowserFactory;
import utility.Helper;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Test extends BaseClass {

    List<WebElement> links;
    List<WebElement> linksOnCricket;
    public List<WebElement> listOfWebstories = null;
    String initialTheme;
    String toggledTheme;
    String parentWindow;
    public HomePage homePage;
    public Helper helper;

    /*This method will run only before every scenario which is tagged with @Test to initialize the driver,
    * start the browser and instantiate the page objects.*/
    @Before("@Test")
    public void login() throws IOException {
        setup();
        startApp();
        homePage = PageFactory.initElements(driver, HomePage.class);
        helper = new Helper(driver);
    }

    /*Asserting that the current url is the valid sports tak url which is stored in config.properties file.*/
    @Given("the user is on SportsTak")
    public void theUserIsOnSportsTak() {
        Assert.assertTrue(driver.getCurrentUrl().contains(config.getUrl()));
    }

    /*The click action method from the page class is called to perform the click operation.*/
    @When("the user clicks on language selection button")
    public void theUserClicksOnLanguageSelectionButton() {
        homePage.clickOnLanguageButton();
    }

    /*Asserting that the current url is the valid hindi sports tak url after selecting the hindi language.*/
    @Then("the language gets changed")
    public void theLanguageGetsChanged() {
        Assert.assertTrue(driver.getCurrentUrl().contains(config.getHindiUrl()));
    }

    /*The user clicks on each thumbnail. From a gerneral user pov, a thumbnail is available mainly on youtube videos.
    * But here there are no such videos. Hence it is assumed that the story picture is a thumbnail. All stories
    * having an <a> tag is stored in a list.*/
    @When("the user clicks on each thumbnail mimicked by sending HTTP request")
    public void theUserClicksOnEachThumbnailMimickedBySendingHTTPRequest() {
        links = driver.findElements(By.tagName("a"));
    }

    /*A click operation to validate the link is mimicked on each list items using HTTP method to improve execution time.*/
    @Then("ensure that the urls are not broken")
    public void ensuresThatTheUrlsAreNotBroken() {
        helper.verifyLink(links, config);
    }

    /*The cricket labeled button is clicked.*/
    @When("the user clicks on the Cricket button")
    public void theUserClicksOnTheCricketButton() {
        homePage.clickOnCricketButton();
    }

    /*JavaScript executor object is used to perform scrolling operation. Since there are nester scroll bar, the main
    * area needs to be selected for a particular scroll bar to be active. Hence Scores text is clicked. Since the
    * scrolling should occur till page 3, a scrollCount counter is used to track the amount of scrolling down. Each scroll
    * operation is done till the end of the available body in the DOM.*/
    @And("scrolls down to third page")
    public void scrollsDownToThirdPage() throws InterruptedException {
        driver.findElement(By.xpath("//h2[contains(text(),'Scores')]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int scrollCount = 2;
        long intialLength = (long) js.executeScript("return document.body.scrollHeight");
        while(scrollCount > 0){
            js.executeScript("window.scrollTo(0,document.body.scrollHeight);", "");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long currentLength = (long) js.executeScript("return document.body.scrollHeight");
            if(intialLength == currentLength) {
                break;
            }
            intialLength = currentLength;
            scrollCount--;
        }
        Thread.sleep(10000);
    }

    /*All links with an <a> tag till page 3 of cricket are validated.*/
    @Then("click and verify that links are not broken")
    public void clickAndVerifyThatLinksAreNotBroken() {
        linksOnCricket = driver.findElements(By.xpath("//div[@class='right-section']/div/div/a"));
        helper.verifyLink(linksOnCricket, config);
    }

    /*The theme selection button is clicked. But before the click, the initial background color is stored in initialTheme
    * var, and after the click the background color is stored in toggledTheme var which are later used for assertion.
    * A sleep is used here since the change of background color take some time and depends on connection speed.*/
    @When("the user clicks on the theme button")
    public void theUserClicksOnTheThemeButton() throws InterruptedException {
        initialTheme = driver.findElement(By.xpath("//body")).getCssValue("background-color");
        homePage.clickOnThemeButton();
        Thread.sleep(5000);
        toggledTheme = driver.findElement(By.xpath("//body")).getCssValue("background-color");
    }

    /*The initialTheme and toggledTheme variables are used to assert whether the button actually worked. The dark and
    * light theme rgba colors of the background are stored in the config.properties file.*/
    @Then("the website turns on that theme")
    public void theWebsiteTurnsOnThatTheme() {
        if(initialTheme.equals(config.getDarkThemeColors())) {
            Assert.assertEquals(toggledTheme, config.getLightThemeColors());
        } else {
            Assert.assertEquals(toggledTheme, config.getDarkThemeColors());
        }
    }

    /*Since clicking on a web story opens a new window, it is important to store the parent window handle to
    * get back control of the main page. Hence, it is stored. Total number of web stories are stored and the
    * first available story is clicked.*/
    @When("the user clicks on a web story")
    public void theUserClicksOnAWebStory() {
        parentWindow = driver.getWindowHandle();
        listOfWebstories = homePage.getListOfWebStories();
        listOfWebstories.get(0).click();
    }

    /*Store the window handles as the web story opens in a new window. Switch to it and list all the story pages
    * and print it in the console. After the operation, close the window and get back to main window and get its
    * control.*/
    @Then("the web story opens in a new tab and story texts are captured in console")
    public void theWebStoryOpensInANewTabAndStoryTextsAreCapturedInConsole() throws InterruptedException {
        Set<String> s = driver.getWindowHandles();
        for (String childWindow : s) {
            if (!parentWindow.equals(childWindow)) {
                /*Switch control to web story window*/
                driver.switchTo().window(childWindow);
                /*Store all the pages in story in a list.*/
                List<WebElement> stories = driver.findElements(By.xpath("//amp-story-page"));
                List<WebElement> ads = driver.findElements(By.xpath("//amp-story-page[contains(@id, 'i-amphtml-ad-page-')]"));
                /*Print the text on each page in the console. The condition inside the while loop is for checking the click count. As per calculation
                * it should be total story page - total ads. But ads are always captured as 1. Hence for for bigger web story there
                * are more number of ads visible which can make test unstable. This is handled using the next while loop.*/
                int page = 0;
                while(page < stories.size()-ads.size()) {
                    System.out.println(stories.get(page).getText());
                    driver.findElement(By.xpath("//div[contains(@class, 'next-container')]")).click();
                    page++;
                    Thread.sleep(1000);
                }
                /*Here it is checked whether the last story is reached by checking for the replay button. If not continue the operation.
                * It is separated, to improve speed.*/
                while(!helper.isElementPresent(driver.findElements(By.xpath("//div[contains(@class, 'replay')]")))) {
                    System.out.println(stories.get(page++).getText());
                    driver.findElement(By.xpath("//div[contains(@class, 'next-container')]")).click();
                    Thread.sleep(1000);
                }
                System.out.println(stories.get(stories.size()-1).getText());
                /*On the last page there is a button to go to the next story, but it is inside a shadow element of the DOM.
                * For selenium to access it, the root of the shadow is captured and then the element is found inside the
                * shadow. Then the action class is used to move to that element and click on it.*/
                WebElement shadowHost = driver.findElement(By.xpath("//div[@class='i-amphtml-story-page-open-attachment-host']"));
                SearchContext shadowRoot = shadowHost.getShadowRoot();
                WebElement shadowContent = shadowRoot.findElement(By.className("i-amphtml-story-page-attachment-label"));
                Actions action = new Actions(driver);
                action.moveToElement(shadowContent).click().perform();
                Thread.sleep(5000);
            }
        }
        driver.close();
        driver.switchTo().window(parentWindow);
        Thread.sleep(5000);
    }

    /*This will run after every scenario tagged with @Test. This method will check if the scenario failed.
    * If a test fails, a screenshot will be captured and the browser will close*/
    @After("@Test")
    public void tearDown(Scenario scenario) throws InterruptedException {
        if (scenario.isFailed()) {
            Thread.sleep(2000);
            scenario.attach(Helper.captureScreenshot(driver), "image/png", "Screenshot");
        }
        BrowserFactory.quitBrowser(driver);
    }
}
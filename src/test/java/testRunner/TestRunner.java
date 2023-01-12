package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*This is the entry point to execute cucumber test.
 * plugin - type of report generated.
 * features - the path to the feature files.
 * glue - the package having the step definition for the feature files.*/
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features = {"src/test/resources"},
        glue = {"stepDefinitions"},
        tags = "@ChangeLanguage"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}

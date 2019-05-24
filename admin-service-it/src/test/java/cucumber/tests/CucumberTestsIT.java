package cucumber.tests;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(strict = true, features = { "classpath:cucumber/featurefiles" }, plugin = { "pretty",
		"html:target/cucumber-html-report" }, tags = {}, glue = { "classpath:cucumber/tests",
				"classpath:com/demo/tests" })
public class CucumberTestsIT {
}

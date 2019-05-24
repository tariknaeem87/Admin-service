package cucumber.tests;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class RESTSetupStepDefs extends AbstractContextStepDefs {
	
    @Before(order = -1000)
    public void beforeScenario(Scenario scenario) {

    }

    @After(order = -1000)
    public void afterScenario(Scenario scenario) {

    }
}
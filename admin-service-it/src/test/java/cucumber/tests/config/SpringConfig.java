package cucumber.tests.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan( basePackages = {
        "cucumber.tests"
})
@Configuration
public class SpringConfig {
	
	@Bean(name="cucumber.tests.RESTSetupStepDefs")
	public cucumber.tests.RESTSetupStepDefs restSetupStepDefs(){
		return new cucumber.tests.RESTSetupStepDefs();
	}

}
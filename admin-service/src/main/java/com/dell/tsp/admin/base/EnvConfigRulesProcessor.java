package com.dell.tsp.admin.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

public class EnvConfigRulesProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment configurableEnvironment, SpringApplication springApplication) {
        assertProfiles(configurableEnvironment.getDefaultProfiles());
        assertProfiles(configurableEnvironment.getActiveProfiles());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private void assertProfiles(String[] profiles) {
        for (int i = 0; i < profiles.length; i++) {
            String profile = profiles[i];
            Assert.doesNotContain(profile, " ", String.format("Profile name '%s' contains space and not allowed. Please correct the name and try again.", profile));
        }
    }
}

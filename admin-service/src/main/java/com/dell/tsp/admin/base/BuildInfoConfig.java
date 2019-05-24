package com.dell.tsp.admin.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "build.info.file")
@ConfigurationProperties(prefix = "build.info")
public class BuildInfoConfig {

    private Resource file;

    @Autowired
    private Environment environment;

    @Bean
    public BuildInfoContrib buildInfoContributor() throws IOException {
        Yaml yaml = new Yaml();
        @SuppressWarnings("unchecked")
		Map<String,Object> details = (Map<String,Object>) yaml.load(file.getInputStream());
        details.put("active.profiles", environment.getActiveProfiles());
        details.put("default.profiles", environment.getDefaultProfiles());

        return new BuildInfoContrib(details);
    }

    public void setFile(Resource file) {
        this.file = file;
    }

}
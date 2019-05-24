package com.dell.tsp.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class Application implements EnvironmentAware {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		System.out.println("running successfully");
	}

	@Override
	public void setEnvironment(Environment environment) {
		LOG.info("Application running with active profiles: " + Arrays.toString(environment.getActiveProfiles()));
	}
}

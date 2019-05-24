package com.dell.tsp.admin;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(value ={"spring.config.location:classpath:deployment.test.yml"}, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = {Application.class}, initializers=ConfigFileApplicationContextInitializer.class)
public abstract class AbstractApiIntegrationTest {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;


    @Before
    public void setup()
    {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }



}



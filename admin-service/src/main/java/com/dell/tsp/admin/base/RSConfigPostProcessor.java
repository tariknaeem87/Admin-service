package com.dell.tsp.admin.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;

public class RSConfigPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSConfigPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ResourceServerConfiguration) {
            LOGGER.debug("Lowering order of ResourceServerConfiguration bean : {}", beanName);
            ResourceServerConfiguration config = (ResourceServerConfiguration) bean;
            config.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}

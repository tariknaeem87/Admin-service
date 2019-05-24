package com.dell.tsp.admin.base;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.demo.commons.logging.LoggingRequestInterceptor;

import java.util.List;

public class RestTemplateLoggingCustomizer implements RestTemplateCustomizer {

    private final LoggingRequestInterceptor loggingRequestInterceptor;

    public RestTemplateLoggingCustomizer(final LoggingRequestInterceptor loggingRequestInterceptor){
        this.loggingRequestInterceptor = loggingRequestInterceptor;
    }

    @Override
    public void customize(RestTemplate restTemplate) {

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        for (ClientHttpRequestInterceptor interceptor : interceptors) {
            if(interceptor instanceof LoggingRequestInterceptor){
                return;
            }
        }
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(restTemplate.getRequestFactory()));
        interceptors.add(loggingRequestInterceptor);
    }
}

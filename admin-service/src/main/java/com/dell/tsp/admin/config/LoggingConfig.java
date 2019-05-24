package com.dell.tsp.admin.config;

import com.dell.tsp.admin.base.RestTemplateLoggingCustomizer;
import com.demo.commons.logging.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "request.logging.enabled", havingValue ="true")
public class LoggingConfig {

    private static final String REQUEST_LOGGER_NAME = "request.trace";

    @ConditionalOnProperty(name ="request.logging.secure.enabled", havingValue = "false", matchIfMissing = true)
    @Bean(name = "requestTraceLogWriter")
    public LogWriter verboseLogWriter() {
        return new LogWriter(REQUEST_LOGGER_NAME);
    }

    @ConditionalOnProperty(name ="request.logging.verbose", havingValue = "true", matchIfMissing = false)
    @Bean(name = "requestTraceLoggingRequestInterceptor")
    public LoggingRequestInterceptor verboseLoggingRequestInterceptor(@Qualifier("requestTraceLogWriter") LogWriter logWriter) {
        return new VerboseLoggingRequestInterceptor(logWriter);
    }

    @ConditionalOnProperty(name ="request.logging.verbose", havingValue = "false", matchIfMissing = true)
    @Bean(name = "requestTraceLoggingRequestInterceptor")
    public LoggingRequestInterceptor jsonLoggingRequestInterceptor(@Qualifier("requestTraceLogWriter") LogWriter logWriter) {
        return new JsonLoggingRequestInterceptor(logWriter);
    }

    @Bean
    public RestTemplateCustomizer restTemplateLoggingCustomizer(@Qualifier("requestTraceLoggingRequestInterceptor") LoggingRequestInterceptor loggingRequestInterceptor) {
        RestTemplateLoggingCustomizer restTemplateLoggingCustomizer = new RestTemplateLoggingCustomizer(loggingRequestInterceptor);
        return restTemplateLoggingCustomizer;
    }
}

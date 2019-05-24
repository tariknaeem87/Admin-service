package com.dell.tsp.admin.config;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.demo.commons.web.DefaultExceptionHandler;
import com.demo.commons.web.ErrorConverter;
import com.demo.commons.web.NamedExceptionHandler;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration

public class RestConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		MappingJackson2HttpMessageConverter converter= new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
		converter.setObjectMapper(myObjectMapper());
		
		return builder.messageConverters(
				new FormHttpMessageConverter(),
				new StringHttpMessageConverter(),
				converter
				).build();
	}
	
	public ObjectMapper myObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    return objectMapper;
	}
	
	@Bean
    public ErrorConverter errorConverter(){
        return new ErrorConverter();
    }

    @Bean
    public DefaultExceptionHandler defaultExceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Bean
    public NamedExceptionHandler namedExceptionHandler() {
        return new NamedExceptionHandler(errorConverter(), localeResolver());
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new AcceptHeaderLocaleResolver();
    }
}

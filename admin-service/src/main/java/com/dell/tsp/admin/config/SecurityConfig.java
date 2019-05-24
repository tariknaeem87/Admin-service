package com.dell.tsp.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = {"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**","/api/v1/signin/**" };
	private static final String[] EXTN_WHITELIST = {"/", "/favicon.ico", "/**/*.png",
			"/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js" };
	@Value("${security.enable-csrf}")
    private boolean csrfEnabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
        if (!csrfEnabled) {
            http.csrf().disable();
     }
      
        http
   .httpBasic();

        
		http.authorizeRequests()
		.antMatchers(AUTH_WHITELIST).permitAll()
		.antMatchers(EXTN_WHITELIST).permitAll();
		http.csrf().disable();
	}
	

}
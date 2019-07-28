package com.example.springsecuritywebmvc.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

// By just extending AbstractSecurityWebApplicationInitializer, Spring security filter chain is created 
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
	
	public SecurityWebApplicationInitializer() {
        // For this setting to work getRootConfigClasses() must return null in web application initializer
		// Otherwise below statement fails
		super(WebSecurityConfig.class);
    }
 
    @Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
    	super.insertFilters(servletContext, new CustomSpringSecurityFilterChain());
	}
}

package com.example.springsecuritywebmvc.config;

import org.springframework.web.servlet.support.*;

// Alternative for web.xml 
// WebApplicationInitializer to register a DispatcherServlet and use Java-based Spring configuration.
// If an application context hierarchy is not required, applications may return all configuration via getRootConfigClasses()
// and return null from getServletConfigClasses().

public class WebAppConfigInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	// For "root" application context (non-web infrastructure) configuration.
	// The configuration for the root application context, 
	// or null if creation and registration of a root context is not desired.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		//return new Class<?>[] { RootConfig.class };
		return null;
	}

	// For DispatcherServlet application context (Spring MVC infrastructure) configuration.
	// The configuration for the Servlet application context, 
	// or null if all configuration is specified through root config classes.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] { WebConfig.class };
	}

	
	// Specify the servlet mapping(s) for the DispatcherServlet
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
}

package com.example.springsecuritybasics.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

/* Spring Security provides a number of filters by default, and most of the time, these are enough. 
 * Sometimes it’s necessary to implement new functionality by creating a new filter to use in the chain.
 */

// GenericFilterBean - Simple base implementation of Filter which treats its config parameters 
// (init-param entries within the filter tag in web.xml) as bean properties.
@Configuration
public class CustomFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Inside CustomFilter ...");
		
		// Causes the next filter in the chain to be invoked, or if the calling filter is the last filter in the chain, 
		// causes the resource at the end of the chain to be invoked.
		chain.doFilter(request, response);
	}
}


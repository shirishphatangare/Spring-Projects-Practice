package com.in28minutes.soap.webservices.soapcoursemanagement.config;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;

import com.in28minutes.soap.webservices.soapcoursemanagement.util.Util;

public class LogMessageTracingEndpointInterceptor implements EndpointInterceptor {

	  @Override
	  public void afterCompletion(MessageContext arg0, Object arg1, Exception arg2) throws Exception {
	    // No-op
	  }

	  @Override
	  public boolean handleFault(MessageContext messageContext, Object arg1) throws Exception {
		System.out.println("Inside handleFault..");
	  	Util.logMessage("Server sending Fault", messageContext.getResponse());
		  
	    return true;
	  }

	  @Override
	  public boolean handleRequest(MessageContext messageContext, Object arg1) throws Exception {
	    System.out.println("Inside handleRequest..");
		Util.logMessage("Server Received Request Message", messageContext.getRequest());

	    return true;
	  }

	  @Override
	  public boolean handleResponse(MessageContext messageContext, Object arg1) throws Exception {
		System.out.println("Inside handleResponse..");
		Util.logMessage("Server Response Message", messageContext.getResponse());

	    return true;
	  }
	}



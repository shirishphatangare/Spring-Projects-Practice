package com.spring.soap.consumer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import com.spring.soap.consumer.util.Util;

// Adding Client Message Tracing Logging

public class LogMessageTracingClientInterceptor implements ClientInterceptor {

	  @Override
	  public void afterCompletion(MessageContext arg0, Exception arg1)
	      throws WebServiceClientException {
	    // No-op
	  }

	  @Override
	  public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
		  System.out.println("Inside handleFault..");
	  	  Util.logMessage("Client Received Fault", messageContext.getResponse());
	    
	  	return true;
	  }

	  @Override
	  public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
		  System.out.println("Inside handleRequest..");
		  Util.logMessage("Client Request Message", messageContext.getRequest());

		return true;
	  }

	  @Override
	  public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
		  System.out.println("Inside handleResponse..");
		  Util.logMessage("Client Received Response Message", messageContext.getResponse());

	    return true;
	  }
	}

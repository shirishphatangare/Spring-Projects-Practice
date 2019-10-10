package com.spring.boot.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner{

	@Autowired
	FooClient fooClient;
	
	@Override
	public void run(String... args) throws Exception {
		
		fooClient.getAllFoosInPlainJSON();
		fooClient.getFooInPlainJSON();
		fooClient.getFooAsPOJO();
		fooClient.getAllHeadersInResponse();
		
		fooClient.createFooAndGetPlainJSON();
		fooClient.createFooAndGetPOJO();
		fooClient.createFooAndGetLocation();
		
		fooClient.createFooBySubmitFormData();

		fooClient.createFooUsingExchangeAPI();
		
		
		fooClient.getAllowedOperationsUsingOPTIONS();
		fooClient.updateFooUsingExchangeAPI();
		fooClient.deleteFooUsingExchangeAPI();
		
		fooClient.getAllFoosUsingParameterizedTypeReference();
		
		fooClient.testConnectionTimeout(); // with Spring retry
		fooClient.testNotFoundExceptionGenericErrorHandling();

	}
	
}

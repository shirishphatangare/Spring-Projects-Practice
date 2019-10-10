package com.spring.boot.restTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.restTemplate.pojo.Foo;


@Component
public class FooClient {

	@Autowired
	RestTemplate restTemplate;
	
	public void getAllFoosInPlainJSON() throws IOException {
		String fooResourceUrl
		  = "http://localhost:8082/spring-rest/foos";
		
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl , String.class);
		
		String foos = response.getBody();
		System.out.println("Printing all Foos in getAllFoosInPlainJSON: " + foos);
    }
	
	
	
	public void getFooInPlainJSON() throws IOException {
		String fooResourceUrl
		  = "http://localhost:8082/spring-rest/foos";
		
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl + "/1" , String.class);
		
		String foo = response.getBody();
		System.out.println("Printing Foo in getFooInPlainJSON: " + foo);
		
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(foo);
		JsonNode name = root.path("name");
		System.out.println("Printing name of Foo in getFooInPlainJSON: " + name.asText());
    }
	
	
	public void getFooAsPOJO() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		Foo foo = restTemplate.getForObject(fooResourceUrl + "/1" , Foo.class);
		System.out.println("Printing Foo with id 1 in getFooAsPOJO: " + foo);
    }
	
	
	public void createFooAndGetPlainJSON() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // without setting header Content-Type to JSON, get 415 error
		 
		HttpEntity<Foo> request = new HttpEntity<>(new Foo(7L, "created sample foo7"), headers);
		ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl, request, String.class);	
		System.out.println("Printing created Foo with id 7 in createFooAndGetPlainJSON: " + response.getBody());
    }
	
	
	public void createFooAndGetPOJO() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // without setting header Content-Type to JSON, get 415 error
		
		HttpEntity<Foo> request = new HttpEntity<>(new Foo(6L, "created sample foo6"), headers);
		Foo foo = restTemplate.postForObject(fooResourceUrl, request, Foo.class);	
		//ResponseEntity response  = restTemplate.postForObject(fooResourceUrl, request, ResponseEntity.class);
		//ResponseEntity<Foo> response = restTemplate.postForEntity(fooResourceUrl, request, Foo.class);	
		System.out.println("Printing created Foo with id 6 in createFooAndGetPOJO: " + foo);
    }
	
	
	public void createFooAndGetLocation() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // without setting header Content-Type to JSON, get 415 error
		
		HttpEntity<Foo> request = new HttpEntity<>(new Foo(8L, "created sample foo8"), headers);
		URI location  = restTemplate.postForLocation(fooResourceUrl, request);	
		System.out.println("Printing Location of created Foo with id 8 in createFooAndGetLocation: " + location);
    }
	

	public void getAllFoosUsingParameterizedTypeReference() {
        ResponseEntity<List<Foo>> response =
                restTemplate.exchange(
                        "http://localhost:8082/spring-rest/foos/",
                        HttpMethod.GET,
                        null, // requestEntity is null for GET request
                        new ParameterizedTypeReference<List<Foo>>(){});

        List<Foo> employees = response.getBody();
        System.out.print("Printing all Foos in getAllFoosUsingParameterizedTypeReference: ");
        employees.forEach(e -> System.out.println(e));
    }
	
	
	
	public void createFooUsingExchangeAPI() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // without setting header Content-Type to JSON, get 415 error
		
		HttpEntity<Foo> request = new HttpEntity<>(new Foo(9L, "created sample foo9"), headers);
		ResponseEntity<Foo> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);
		System.out.println("Printing created Foo with id 9 in createFooUsingExchangeAPI: " + response.getBody());
    }

	
	public void getAllHeadersInResponse() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
		System.out.println("Printing all headers in response: " + httpHeaders);
		
    }

	
	
	public void createFooBySubmitFormData() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // we need to set the “Content-Type” header to application/x-www-form-urlencoded to submit Form data
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>(); // wrap the form variables into a LinkedMultiValueMap:
		map.add("id", "10");
		map.add("name", "created sample foo10");
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl+"/form", request , String.class);	
		System.out.println("Printing created Foo id in createFooBySubmitFormData: " + response.getBody());
    }
	
	
	public void getAllowedOperationsUsingOPTIONS() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);	
		System.out.println("Printing created Foo id in createFooBySubmitFormData: " + optionsForAllow );
    }

	
	public void updateFooUsingExchangeAPI() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON); // without setting header Content-Type to JSON, get 415 error
		
		Foo updatedInstance = new Foo(1L, "updated sample foo1");
		
		HttpEntity<Foo> requestUpdate = new HttpEntity<>(updatedInstance, headers);
		restTemplate.exchange(fooResourceUrl + "/1", HttpMethod.PUT, requestUpdate, Void.class);
		System.out.println("Updated Foo with id 1 - changed name to updated sample foo1");
    }
	
	
	
	public void deleteFooUsingExchangeAPI() throws IOException {
		String fooResourceUrl = "http://localhost:8082/spring-rest/foos";
		restTemplate.delete(fooResourceUrl + "/4");
		System.out.println("Deleted Foo with id 4");
    }
	
	
	
	public void testNotFoundExceptionGenericErrorHandling() throws IOException {
		String fooResourceUrl
		  = "http://localhost:8082/spring-rest/foos";
		
		ResponseEntity<String> response
		  = restTemplate.getForEntity(fooResourceUrl + "/1122" , String.class);
		
		String foo = response.getBody();
		System.out.println("Printing Foo in getFooInPlainJSON: " + foo);
		
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(foo);
		JsonNode name = root.path("name");
		System.out.println("Printing name of Foo in getFooInPlainJSON: " + name.asText());
	}
	
	
    @Retryable(value = { ResourceAccessException.class }, 
		      maxAttempts = 3,
		      backoff = @Backoff(delay = 2000))
	public void testConnectionTimeout() throws ResourceAccessException {
		System.out.println("Hitting unroutable address to yield a connection timeout...");
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		String fooResourceUrl = "http://10.255.255.1:8080"; // 10.255.255.1 is an unroutable address (to yield a connection timeout)
		
		try {
			restTemplate.getForEntity(fooResourceUrl , String.class); // For retry mechanism to work, do not catch Exception here !
		}finally {
			sw.stop();
			int elapsedTime = (int) sw.getTotalTimeMillis();
			System.out.println("Connection Timed out in " + elapsedTime/1000 + " Seconds");
		}
	}
  
	// The @Recover annotation is used to define a separate recovery method when a @Retryable method fails with a specified exception
	// The method annotated with @Recover should take The same type of argument (and optional Throwable or its subclass typed argument). 
	// Besides that the The return type should also be the same.
	// Then only recover method will be fired after the retry exhausted
	    
	  @Recover
	  public void recover(ResourceAccessException e) {
		  System.out.println("Retry exhausted!");
		  System.out.println("Error Class :: " + e.getClass().getName());
	  }
	
	  
	  @Recover
	  public void recover(Exception e) throws Exception {
		  System.out.println("Retry failure due to other than IOException");
		  System.out.println("Error Class :: " + e.getClass().getName());
	      throw e;
	  }

}

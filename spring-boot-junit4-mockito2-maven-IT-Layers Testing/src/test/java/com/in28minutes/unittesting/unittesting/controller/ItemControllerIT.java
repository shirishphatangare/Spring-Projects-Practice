package com.in28minutes.unittesting.unittesting.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) // SpringRunner for Spring Test Context. RunWith tells which runner to use to execute tests
// If you are using JUnit 5, there’s no need to add the equivalent @ExtendWith(SpringExtension.class) as @SpringBootTest and other @…Test annotations are already annotated with it.
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT) // creates the ApplicationContext used in tests. With RANDOM_PORT, it loads a WebServerApplicationContext and provides a real web environment. Embedded servers are started and listen on a random port
public class ItemControllerIT {
	
	@Autowired
	private TestRestTemplate restTemplate; // TestRestTemplate is auto-configured to use port used by WebEnvironment. We just need to provide url
		
	@Test
	public void contextLoads() throws JSONException {	
		String response = this.restTemplate.getForObject("/all-items-from-database", String.class);
		JSONAssert.assertEquals("[{id:10001},{id:10002},{id:10003}]", 
				response, false);
	}

}

// In integration testing an application may depend on a database or external interfaces. A Spring application itself has 
// different layers like Web (Controller), Business(Service) and Data (Repository). During integration testing it is important
// to test flow between these components - Web, Business and Data. Database and external interfaces can be mocked. DB can be mocked by using in-memory DB
// like H2 whereas, external interfaces can be mocked using @MockBean annotation. Basically @MockBean mocks out dependencies you do not want to talk for integration testing by
// replacing them in application context with mocked beans. 

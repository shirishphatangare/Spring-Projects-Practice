package com.in28minutes.unittesting.unittesting.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class) // SpringRunner construct a new SpringRunner and initialize a TestContextManager to provide Spring testing functionality to standard JUnit 4 tests. SpringRunner is an alias for the SpringJUnit4ClassRunner.
// @WebMvcTest Annotation can be used when a test focuses only on Spring MVC components. Using this annotation will disable full auto-configuration and instead apply only configuration relevant to MVC tests.
@WebMvcTest(HelloWorldController.class)
//@TestPropertySource(locations= {"classpath:custom.properties"}) // @TestPropertySource is a class-level annotation that is used to configure the locations() of properties files and inlined properties() to be added to the Environment's set of PropertySources for an ApplicationContext for integration tests
public class HelloWorldControllerTest {
	
	// Using Mock Mvc to Unit test Hello World Controller
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void helloWorld_basic() throws Exception {
		
		// Build a GET "/hello-world" request which accepts application/json
		RequestBuilder request = MockMvcRequestBuilders
				.get("/hello-world")
				.accept(MediaType.APPLICATION_JSON);
		
		// Call request and see if result matched expectations and if yes then return it
		MvcResult result = mockMvc.perform(request)
				// Using matchers like status and content
				.andExpect(status().isOk()) // Checking status 
				.andExpect(content().string("Hello World")) // Checking String Content 
				//.andExpect(content().json("jsonContent")) // To verify json content
				.andReturn();
	
		//Verify result with assertEquals - Alternative to andExpect
		assertEquals("Hello World", result.getResponse().getContentAsString());
	}

}


// Similar to GET request we can also send POST,PUT,DELETE requests 

// POST example
/*String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";

RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
		"/surveys/Survey1/questions")
		.accept(MediaType.APPLICATION_JSON).content(questionJson)
		.contentType(MediaType.APPLICATION_JSON);

MvcResult result = mockMvc.perform(requestBuilder).andReturn(); // // Corresponding MockMvcResultMatchers methods are available too like - .andExpect(status().isCreated()

MockHttpServletResponse response = result.getResponse();

assertEquals(HttpStatus.CREATED.value(), response.getStatus()); 
assertEquals("http://localhost/surveys/Survey1/questions/1", response
		.getHeader(HttpHeaders.LOCATION));*/
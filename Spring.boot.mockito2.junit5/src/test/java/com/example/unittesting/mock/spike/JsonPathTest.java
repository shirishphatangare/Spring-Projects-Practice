package com.in28minutes.unittesting.unittesting.spike;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonPathTest {
	
	// JsonPath: XPath for JSON. Used for JSON parsing. Actual assertions can be used from Junit,AssertJ or Hamcrest. 
	
	@Test
	public void learning() {
		
		// Here you don't have to type escape sequences manually. Just copy-paste a json from .txt file and escape sequence is automatically added.
		String responseFromService = "[" + 
				"{\"id\":10000, \"name\":\"Pencil\", \"quantity\":5}," + 
				"{\"id\":10001, \"name\":\"Pen\", \"quantity\":15}," + 
				"{\"id\":10002, \"name\":\"Eraser\", \"quantity\":10}" + 
				"]";
		
		DocumentContext context = JsonPath.parse(responseFromService);
		
		int length = context.read("$.length()"); // Get length of an array
		assertThat(length).isEqualTo(3); // assertThat is from assertj framework
		
		List<Integer> ids = context.read("$..id"); // Get all elements with key 'id'

		assertThat(ids).containsExactly(10000,10001,10002);
		
		System.out.println(context.read("$.[1]").toString()); 
		System.out.println(context.read("$.[0:2]").toString());
		System.out.println(context.read("$.[?(@.name=='Eraser')]").toString());
		System.out.println(context.read("$.[?(@.quantity==5)]").toString());
	}

}

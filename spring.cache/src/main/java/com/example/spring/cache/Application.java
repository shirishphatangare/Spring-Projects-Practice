package com.example.spring.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching // Enable the processing of the caching annotations
// The @EnableCaching annotation triggers a post processor that inspects every Spring bean for the presence of caching annotations on public methods. 
// If such an annotation is found, a proxy is automatically created to intercept the method call and handle the caching behavior accordingly.
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
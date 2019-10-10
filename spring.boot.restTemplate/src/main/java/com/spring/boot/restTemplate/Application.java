package com.spring.boot.restTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@ComponentScan("com.spring.boot.restTemplate")
@SpringBootApplication
@EnableRetry // Spring Retry provides the ability to automatically re-invoke a failed operation. aop dependency is required
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

package com.spring.soap.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRetry
@EnableScheduling // @EnableScheduling ensures that a background task executor is created. Without it, nothing gets scheduled.
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

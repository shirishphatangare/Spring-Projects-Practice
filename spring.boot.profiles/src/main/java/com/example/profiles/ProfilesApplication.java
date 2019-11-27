package com.example.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProfilesApplication implements CommandLineRunner {

	@Autowired
    private WeatherService weatherService;
	
	@Value("${message}")
	private String message;

    public static void main(String[] args) {
        SpringApplication.run(ProfilesApplication.class, args);
    }

    @Override
    public void run(String... args) {
    	// message property will be read from application-default.properties for default profile if this file is present in classpath
    	// Otherwise message property will be read from application.properties for default profile
    	System.out.println("Message from profile properties: " + message);
        System.out.println(weatherService.forecast());
    }
}

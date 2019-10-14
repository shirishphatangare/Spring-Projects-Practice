package com.spring.soap.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandRunner implements CommandLineRunner {
    @Autowired
    private CourseProcess courseProcess;

    @Override
    public void run(String... args) throws Exception {
    	//courseProcess.process(); // Disabled to run only from scheduled job
    }

}

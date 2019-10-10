package com.packtpub.restapp.ticketmanagement;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packtpub.reactive.Server;

@SpringBootApplication
public class TicketManagementApplication {

	public static void main(String[] args) throws InterruptedException, IOException{
		
		System.out.println("STARTING THE APPLICATION");
		SpringApplication.run(TicketManagementApplication.class, args);
		System.out.println("APPLICATION FINISHED");
		
		
		//Below code can also be moved to TicketManagementCommandLineRunner which implements CommandLineRunner
		//Server server = new Server();
		//server.startReactorServer();

		//System.out.println("Press ENTER to exit.");
		//System.in.read();
	}
}

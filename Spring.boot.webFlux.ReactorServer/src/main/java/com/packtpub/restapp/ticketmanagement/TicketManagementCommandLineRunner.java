package com.packtpub.restapp.ticketmanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.packtpub.reactive.Server;

// @Configuration will also work 
@Component // This is must, otherwise Spring Boot will not automatically call the run method 
public class TicketManagementCommandLineRunner implements CommandLineRunner{

	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("EXECUTING : command line runner");
		
		Server server = new Server();
		server.startReactorServer();

		System.out.println("Press ENTER to exit.");
		System.in.read();
	}
}

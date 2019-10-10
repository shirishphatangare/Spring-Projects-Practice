package com.mastering.spring.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mastering.spring.aop.service.ShoppingService;

@Component
public class ApplicationCommandLineRunner implements CommandLineRunner{

	@Autowired
	ShoppingService shoppingService;
	
	@Override
	public void run(String... args) throws Exception {
		shoppingService.checkAndPlaceOrder();
	}
	
}

package com.mastering.spring.aop.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void placeOrder(int value) {
		// Logic goes here
		logger.info("Placed Order - {}", value);
	}
	

}
package com.mastering.spring.aop.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mastering.spring.aop.model.Stock;

@Repository
public class StockDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public Stock retrieveStock(){
		logger.info("Returning a dummy value");
		//return new Stock(0); // Simulate StockUnavailableException for testing @AfterThrowing
		return new Stock(20);
	}

}
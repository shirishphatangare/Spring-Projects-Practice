package com.mastering.spring.aop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastering.spring.aop.Exception.StockUnavailableException;
import com.mastering.spring.aop.annotations.LogEverything;
import com.mastering.spring.aop.repo.OrderDao;
import com.mastering.spring.aop.repo.StockDao;

@Service
public class ShoppingService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockDao stockDao;

	@Autowired
	private OrderDao orderDao;

	@LogEverything  // Custom AOP annotation
	public void checkAndPlaceOrder() throws StockUnavailableException{
		
		int availableQuantity = stockDao.retrieveStock().getQuantity();

		logger.info("Retrieved Stock - {}", availableQuantity);
		
		if(availableQuantity>0) {
			orderDao.placeOrder(availableQuantity);
		}else {
			throw new StockUnavailableException("Stock unavailable!");
		}
		
	}
}
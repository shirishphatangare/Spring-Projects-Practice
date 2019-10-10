package com.mastering.spring.aop.Exception;

public class StockUnavailableException extends Exception {
	
	private static final long serialVersionUID = 1L;

	String message;
	
	public StockUnavailableException(String message) {
		super(message);
	}
}

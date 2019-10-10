package com.spring.boot.restTemplate.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 594482671578620903L;

	public NotFoundException(String message) {
		super(message);
	}
	
	
}

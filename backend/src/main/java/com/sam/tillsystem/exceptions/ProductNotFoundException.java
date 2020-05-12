package com.sam.tillsystem.exceptions;

public class ProductNotFoundException extends Exception {

	private static final long serialVersionUID = 320317408710609166L;
	
	public ProductNotFoundException(String message) {
		super(message);
	}

}

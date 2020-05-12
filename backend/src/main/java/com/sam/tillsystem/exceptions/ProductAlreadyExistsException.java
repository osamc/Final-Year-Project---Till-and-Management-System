package com.sam.tillsystem.exceptions;

public class ProductAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -7732570800852076822L;
	
	public ProductAlreadyExistsException(String message) {
		super(message);
	}

}

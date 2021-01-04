package com.ingester.exceptions;

public class TransformException extends Exception {
	private static final long serialVersionUID = 1L;

	public TransformException(String errorMessage) {
		super(errorMessage);
	}
}
package com.astrea.core.exceptions;

public class AstreaException extends Exception {
	
    public AstreaException(String message) {
        super(message);
    }
	
	public AstreaException(String message, Throwable cause) {
        super(message, cause);
    }
}

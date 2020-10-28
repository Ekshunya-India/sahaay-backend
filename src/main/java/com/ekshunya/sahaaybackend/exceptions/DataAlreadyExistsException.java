package com.ekshunya.sahaaybackend.exceptions;

public class DataAlreadyExistsException extends BaseApiException{
	public DataAlreadyExistsException(final String message){
		super(message);
		this.statusCode=409; //Conflict
	}

	public DataAlreadyExistsException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 409;
	}

	public DataAlreadyExistsException(final Throwable cause) {
		super(cause);
		this.statusCode = 409;
	}
}

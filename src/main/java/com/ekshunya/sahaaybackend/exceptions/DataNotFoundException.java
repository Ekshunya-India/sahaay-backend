package com.ekshunya.sahaaybackend.exceptions;

public class DataNotFoundException extends BaseApiException{
	public DataNotFoundException(final String message){
		super(message);
		this.statusCode=404;
	}

	public DataNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 404;
	}

	public DataNotFoundException(final Throwable cause) {
		super(cause);
		this.statusCode = 404;
	}
}

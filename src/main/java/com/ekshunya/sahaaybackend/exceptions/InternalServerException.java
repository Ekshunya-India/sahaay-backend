package com.ekshunya.sahaaybackend.exceptions;

public class InternalServerException extends BaseApiException{
	public InternalServerException(final String message){
		super(message);
		this.statusCode=500;
	}
	public InternalServerException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 500;
	}

	public InternalServerException(final Throwable cause) {
		super(cause);
		this.statusCode = 500;
	}
}

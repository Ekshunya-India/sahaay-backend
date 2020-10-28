package com.ekshunya.sahaaybackend.exceptions;

public class UnsupportedMediaTypeException extends BaseApiException{
	public UnsupportedMediaTypeException(final String message){
		super(message);
		this.statusCode = 415;
	}

	public UnsupportedMediaTypeException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 415;
	}

	public UnsupportedMediaTypeException(final Throwable cause) {
		super(cause);
		this.statusCode = 415;
	}
}

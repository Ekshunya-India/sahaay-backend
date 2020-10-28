package com.ekshunya.sahaaybackend.exceptions;

public class BaseApiException extends RuntimeException{
	protected int statusCode = 500;
	protected BaseApiException(final String message){ super(message); }
	protected BaseApiException(final String message,final Throwable cause) {
		super(message, cause);
	}
	public BaseApiException(final Throwable cause) {
		super(cause);
	}
}

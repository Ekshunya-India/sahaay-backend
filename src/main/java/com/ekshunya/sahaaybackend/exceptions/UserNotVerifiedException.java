package com.ekshunya.sahaaybackend.exceptions;

public class UserNotVerifiedException extends BaseApiException{
	public UserNotVerifiedException(final String message){
		super(message);
		statusCode=403;
	}
	public UserNotVerifiedException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 403;
	}

	public UserNotVerifiedException(final Throwable cause) {
		super(cause);
		this.statusCode = 403;
	}
}
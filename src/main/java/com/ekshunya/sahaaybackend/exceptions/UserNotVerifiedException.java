package com.ekshunya.sahaaybackend.exceptions;

public class UserNotVerifiedException extends BaseApiException{
	private int statusCode;
	public UserNotVerifiedException(final String message){
		super(message);
		statusCode=403;
	}
}
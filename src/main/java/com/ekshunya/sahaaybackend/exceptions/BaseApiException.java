package com.ekshunya.sahaaybackend.exceptions;

public class BaseApiException extends RuntimeException{
	protected int statusCode = 500;
	protected BaseApiException(final String message){
		super(message);
	}
}

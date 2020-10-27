package com.ekshunya.sahaaybackend.exceptions;

public class NotImplementedException extends BaseApiException{
	public NotImplementedException(final String message){
		super(message);
		this.statusCode=501;
	}
	private final int statusCode;
}

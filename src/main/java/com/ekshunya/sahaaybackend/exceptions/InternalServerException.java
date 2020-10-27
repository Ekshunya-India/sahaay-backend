package com.ekshunya.sahaaybackend.exceptions;

public class InternalServerException extends BaseApiException{
	public InternalServerException(final String message){
		super(message);
		this.statusCode=500;
	}
}

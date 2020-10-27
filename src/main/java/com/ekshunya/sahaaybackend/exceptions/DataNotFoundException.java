package com.ekshunya.sahaaybackend.exceptions;

public class DataNotFoundException extends BaseApiException{
	public DataNotFoundException(final String message){
		super(message);
		this.statusCode=404;
	}
	//TODO specify the correct Log Format in the Logger and Add appropriate Error code to the Exception
}

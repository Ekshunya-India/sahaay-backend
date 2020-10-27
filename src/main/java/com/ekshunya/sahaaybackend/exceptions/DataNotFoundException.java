package com.ekshunya.sahaaybackend.exceptions;

public class DataNotFoundException extends BaseApiException{
	public DataNotFoundException(){
		this.statusCode=404;
	}
	//TODO specify the correct Log Format in the Logger and Add appropriate Error code to the Exception
}

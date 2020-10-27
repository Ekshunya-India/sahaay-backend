package com.ekshunya.sahaaybackend.exceptions;

public class DataAlreadyExistsException extends BaseApiException{
	public DataAlreadyExistsException(final String message){
		super(message);
		this.statusCode=409; //Conflict
	}
}

package com.ekshunya.sahaaybackend.exceptions;

public class DataAlreadyExistsException extends BaseApiException{
	public DataAlreadyExistsException(){
		this.statusCode=409; //Conflict
	}

}

package com.ekshunya.sahaaybackend.exceptions;

public class InternalServerException extends BaseApiException{
	public InternalServerException(){
		this.statusCode=500;
	}
}

package com.ekshunya.sahaaybackend.exceptions;

public class UnsupportedMediaTypeException extends BaseApiException{
	public UnsupportedMediaTypeException(){
		this.statusCode = 415;
	}
	private final int statusCode;
}

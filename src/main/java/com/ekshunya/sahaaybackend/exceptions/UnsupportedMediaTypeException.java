package com.ekshunya.sahaaybackend.exceptions;

public class UnsupportedMediaTypeException extends BaseApiException{
	public UnsupportedMediaTypeException(final String message){
		super(message);
		this.statusCode = 415;
	}
	private final int statusCode;
}

package com.ekshunya.sahaaybackend.exceptions;

public class NotImplementedException extends BaseApiException{
	public NotImplementedException(){
		this.statusCode=501;
	}
	private final int statusCode;
}

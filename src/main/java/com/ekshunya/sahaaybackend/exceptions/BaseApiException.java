package com.ekshunya.sahaaybackend.exceptions;

public class BaseApiException extends RuntimeException{
	protected int statusCode = 500;
}

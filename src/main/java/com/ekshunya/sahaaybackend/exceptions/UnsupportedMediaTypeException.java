package com.ekshunya.sahaaybackend.exceptions;

public class UnsupportedMediaTypeException extends RuntimeException{
	private final int statusCode = 415;
}

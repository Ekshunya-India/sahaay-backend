package com.ekshunya.sahaaybackend.exceptions;

public class NoContentException extends RuntimeException{
	private final int httpStatusCode = 204;
}

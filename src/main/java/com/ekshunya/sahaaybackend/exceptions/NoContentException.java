package com.ekshunya.sahaaybackend.exceptions;

public class NoContentException extends BaseApiException{
	public NoContentException(final String message) {
		super(message);
		this.statusCode=204;
	}
}

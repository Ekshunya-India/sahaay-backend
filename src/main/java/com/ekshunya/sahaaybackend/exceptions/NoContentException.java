package com.ekshunya.sahaaybackend.exceptions;

public class NoContentException extends BaseApiException{
	public NoContentException() {
		this.statusCode=204;
	}
}

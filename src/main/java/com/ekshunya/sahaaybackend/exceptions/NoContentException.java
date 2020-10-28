package com.ekshunya.sahaaybackend.exceptions;

public class NoContentException extends BaseApiException{
	public NoContentException(final String message) {
		super(message);
		this.statusCode=204;
	}

	public NoContentException(final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = 204;
	}

	public NoContentException(final Throwable cause) {
		super(cause);
		this.statusCode = 204;
	}
}

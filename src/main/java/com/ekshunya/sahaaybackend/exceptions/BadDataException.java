package com.ekshunya.sahaaybackend.exceptions;

public class BadDataException extends BaseApiException {

    public BadDataException(final String message) {
        super(message);
        this.statusCode = 400;
    }

    public BadDataException(final String message, final Throwable cause) {
        super(message, cause);
        this.statusCode = 400;
    }

    public BadDataException(final Throwable cause) {
        super(cause);
        this.statusCode = 400;
    }
}

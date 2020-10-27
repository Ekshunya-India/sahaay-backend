package com.ekshunya.sahaaybackend.exceptions;

public class BadDataException extends BaseApiException{

    public BadDataException(final String message) {
        super(message);
        this.statusCode = 400;
    }
}

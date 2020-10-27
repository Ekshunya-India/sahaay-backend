package com.ekshunya.sahaaybackend.exceptions;

public class UserNotVerifiedException {
	private int statusCode;
	public UserNotVerifiedException(){
		statusCode=403;
	}
}
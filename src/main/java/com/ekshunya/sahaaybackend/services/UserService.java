package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.model.dtos.PhoneNumberAddDto;
import com.ekshunya.sahaaybackend.model.dtos.UserDto;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class UserService {
	private final MongoDatabase mongoDatabase;
	public UserService(final MongoDatabase mongoDatabase){
		this.mongoDatabase = mongoDatabase;
	}

	public UserDto addPhoneNumber(final PhoneNumberAddDto phoneNumberAddDto) {
		//TODO add code to call mongoDB and add the user phone number
		return null;
	}
}

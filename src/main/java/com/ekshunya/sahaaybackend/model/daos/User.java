package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import java.util.UUID;

@Value
public class User {
	UUID userId;
	UserType userType;
	String avatarUrl;
	String userName;
}

package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.Digits;
import java.util.UUID;

@Value
public class User {
	UUID userId;
	UserType userType;
	String avatarUrl;
	String userName;
	String userPhoneNumber;
}

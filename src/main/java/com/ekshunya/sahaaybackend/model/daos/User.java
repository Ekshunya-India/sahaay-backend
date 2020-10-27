package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.Digits;
import java.util.UUID;

@Value
public class User {
	UUID id;
	UserType type;
	String avatarUrl;
	String name;
	String phoneNumber;
}

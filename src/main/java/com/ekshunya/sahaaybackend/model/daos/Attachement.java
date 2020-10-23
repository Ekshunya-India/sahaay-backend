package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import java.util.UUID;

@Value
public class Attachement {
	AttachementType attachementType;
	String url;
	Update correspondingUpdate;
	UUID id; //Generated.
}

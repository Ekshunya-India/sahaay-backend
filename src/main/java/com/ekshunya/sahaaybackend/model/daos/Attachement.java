package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class Attachement {
	@NotNull
	AttachementType attachementType;
	String url;
	Feed feed;
	UUID id; //Generated.
}

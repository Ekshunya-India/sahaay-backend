package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class SahaayLocation {
	@NotNull(message = "latitude cannot be null")
	double latitude;
	@NotNull(message = "longitude cannot be null")
	double longitude;
}

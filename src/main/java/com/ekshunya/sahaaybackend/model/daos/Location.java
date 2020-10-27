package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class Location {
	@NotNull(message = "longitude cannot be null")
	double lng;
	@NotNull(message = "latitude cannot be null")
	double lat;
}

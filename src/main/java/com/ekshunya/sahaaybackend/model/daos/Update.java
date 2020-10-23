package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class Update {
	UUID updateId; //Generated.
	ZonedDateTime created;
	ZonedDateTime lastUpdated;
	List<Attachement> attachmentList;
	User userId; //Optional
}
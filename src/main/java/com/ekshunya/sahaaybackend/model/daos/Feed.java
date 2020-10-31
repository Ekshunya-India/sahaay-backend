package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;
import org.bson.codecs.pojo.annotations.BsonId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class Feed {
	@BsonId
	UUID id; //Generated.
	@NotNull(message = "There should always be a created date time associated with the update")
	@PastOrPresent
	ZonedDateTime created;
	ZonedDateTime lastUpdated;
	@NotNull()
	List<Attachement> attachments;
	User userId; //Optional
}
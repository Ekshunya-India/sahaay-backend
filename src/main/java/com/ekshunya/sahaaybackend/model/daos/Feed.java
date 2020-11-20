package com.ekshunya.sahaaybackend.model.daos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.bson.codecs.pojo.annotations.BsonId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feed {
	@BsonId
	private UUID id;
	@NotNull(message = "There should always be a created date time associated with the update")
	@PastOrPresent
	private ZonedDateTime created;
	private ZonedDateTime lastUpdated;
	@NotNull()
	private List<Attachment> attachments;
	private UUID userId; //Optional
}
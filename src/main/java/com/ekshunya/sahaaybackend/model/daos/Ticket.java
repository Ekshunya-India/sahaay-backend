package com.ekshunya.sahaaybackend.model.daos;

import com.googlecode.jmapper.annotations.JMap;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
//TODO Read this and make sure that we follow the right data model for Ticket as this is the main data class
// https://docs.mongodb.com/manual/core/data-model-design/ In general, embedding provides better performance for read operations, as well as the ability to request and retrieve related data in a single database operation. Embedded data models make it possible to update related data in a single atomic write operation.
// https://docs.mongodb.com/manual/tutorial/model-referenced-one-to-many-relationships-between-documents/#data-modeling-publisher-and-books
// Use shorter field names. description can be des, ticketOpenedBy can be openedBy, https://docs.mongodb.com/manual/core/data-model-operations/#storage-optimization-for-small-documents
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	@BsonId
	@JMap
	private UUID id;
	@JMap
	@NotNull(message = "The ticket title cannot be null")
	private String title;
	@JMap
	@NotNull(message = "The ticket description cannot be null")
	private String desc;
	@JMap
	@NotNull(message = "The created Date cannot be null")
	@PastOrPresent
	private ZonedDateTime created;
	@JMap
	@FutureOrPresent
	private ZonedDateTime expectedEnd;
	@JMap
	private ZonedDateTime actualEnd;
	@JMap
	private UUID openedBy;
	@JMap
	private UUID assignedTo;
	@JMap
	@NotNull(message = "The location associated to the ticket cannot be null")
	private Location location;
	@JMap
	@NotNull(message = "A Ticket must have a default priority")
	private Priority priority;
	@JMap
	@NotNull(message = "A Ticket must be valid type")
	private TicketType ticketType;
	@JMap
	@NotNull(message = "A Ticket must be in a valid State")
	private State state;
	@JMap
	private int visibility;
	@JMap
	private int upvotes;
	@JMap
	private int downvotes;
	@JMap
	private List<Feed> feeds;
	@JMap
	private List<Comment> comments;
	@JMap
	private List<String> tags;
}
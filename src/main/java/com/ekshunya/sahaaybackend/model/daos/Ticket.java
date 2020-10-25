package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
//TODO Read this and make sure that we follow the right data model for Ticket as this is the main data class
// https://docs.mongodb.com/manual/core/data-model-design/ In general, embedding provides better performance for read operations, as well as the ability to request and retrieve related data in a single database operation. Embedded data models make it possible to update related data in a single atomic write operation.
// https://docs.mongodb.com/manual/tutorial/model-referenced-one-to-many-relationships-between-documents/#data-modeling-publisher-and-books
@Value
public class Ticket {
	UUID id;
	@NotNull(message = "The ticket title cannot be null")
	String title;
	@NotNull(message = "The ticket description cannot be null")
	String description;
	@NotNull(message = "The created Date cannot be null")
	@PastOrPresent
	ZonedDateTime created;
	@FutureOrPresent
	ZonedDateTime expectedEnd;
	ZonedDateTime actualEnd;
	User ticketOpenedBy;
	User ticketAssignedTo;
	@NotNull(message = "The location associated to the ticket cannot be null")
	Location location;
	@NotNull(message = "A Ticket must have a default priority")
	Priority priority;
	@NotNull(message = "A Ticket must be valid type")
	TicketType ticketType;
	@NotNull(message = "A Ticket must be in a valid State")
	States currentState;
	int visibility;
	int upvotes;
	int downvotes;
	List<Update> updates;
	List<Comment> comments;
	List<String> tags;
}

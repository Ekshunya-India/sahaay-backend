package com.ekshunya.sahaaybackend.model.daos;

import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Value
public class Ticket {
	@NonNull
	UUID id;
	@NonNull
	String title;
	String description;
	@NonNull
	ZonedDateTime created;
	ZonedDateTime expectedEnd;
	@NonNull
	ZonedDateTime actualEnd;
	User ticketOpenedBy;
	User ticketAssignedTo;
	@NonNull
	SahaayLocation location;
	@NonNull
	Priority priority;
	TicketType ticketType;
	@NonNull
	States currentState;
	int visibility;
	int upvotes;
	int downvotes;
	List<Update> updates;
	List<Comment> comments;
	List<String> tags;
}

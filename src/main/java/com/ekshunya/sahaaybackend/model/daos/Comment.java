package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
public class Comment {
	UUID id;
	@NotNull(message = "Comment String cannot be Null in a comment")
	String comment;
	@NotNull(message = "A Comment cannot be an orphan comment")
	UUID userId;
	@NotNull(message = "The Comment must be associated to some Ticket. Comment cannot be orphans")
	UUID ticketId;
}

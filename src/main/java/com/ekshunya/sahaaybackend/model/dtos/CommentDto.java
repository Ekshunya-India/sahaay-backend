package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

@Value
public class CommentDto {
	String id;
	String comment;
	String userId;
	String ticketId;
}

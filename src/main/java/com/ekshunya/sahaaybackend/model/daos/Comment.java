package com.ekshunya.sahaaybackend.model.daos;

import lombok.Value;

import java.util.UUID;

@Value
public class Comment {
	String comment;
	UUID commentId;
	UUID userId;
}

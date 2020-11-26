package com.ekshunya.sahaaybackend.messages;

import com.ekshunya.sahaaybackend.model.daos.Priority;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class MessageBase {
	protected final String message;
	protected final UUID userId;
	protected final Priority priority;
}
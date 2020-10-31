package com.ekshunya.sahaaybackend.model.dtos;

import io.undertow.server.handlers.form.FormData;
import lombok.Value;

import java.util.UUID;

@Value
public class TicketFeedDto {
	UUID id;
	FormData formData;
	UUID userId;
}
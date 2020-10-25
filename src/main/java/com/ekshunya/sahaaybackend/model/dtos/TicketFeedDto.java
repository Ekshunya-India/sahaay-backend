package com.ekshunya.sahaaybackend.model.dtos;

import io.undertow.server.handlers.form.FormData;
import lombok.Value;

@Value
public class TicketFeedDto {
	String ticketId;
	FormData formData;
}

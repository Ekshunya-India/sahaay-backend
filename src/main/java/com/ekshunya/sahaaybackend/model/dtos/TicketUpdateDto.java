package com.ekshunya.sahaaybackend.model.dtos;

import io.undertow.server.handlers.form.FormData;
import lombok.Value;

@Value
public class TicketUpdateDto {
	String ticketId;
	FormData formData;
}

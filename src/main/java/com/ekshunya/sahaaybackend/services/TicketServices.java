package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class TicketServices {
	private final MongoDatabase mongoDatabase;

	@Inject
	public TicketServices(final MongoDatabase mongoDatabase){
		this.mongoDatabase = mongoDatabase;
	}


	public TicketDto createTicket(final TicketCreateDto ticketCreateDto) {
		//TODO connect with MongoDB Ticket Collection and create the Ticket.
		return null;
	}

	public TicketDto updateTicket(final TicketDetailsUpdateDto ticketDetailsUpdateDto) {
		//TODO update the Ticket in Mongo DB using the Async Drivers. Return value might change here as Mongo might return a Publisher.
		return null;
	}
}

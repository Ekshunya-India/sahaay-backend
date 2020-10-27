package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.model.daos.Feed;
import com.ekshunya.sahaaybackend.model.daos.Ticket;
import com.ekshunya.sahaaybackend.model.daos.TicketType;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketService {
	private final MongoDatabase mongoDatabase;

	@Inject
	public TicketService(final MongoDatabase mongoDatabase){
		this.mongoDatabase = mongoDatabase;
	}


	public Ticket createANewTicket(final Ticket ticketToSave) {
		//TODO connect with MongoDB Ticket Collection and create the Ticket.
		return null;
	}

	public Ticket updateTicket(final Ticket ticketToUpdate) {
		return null;
	}

	public Ticket fetchTicket(final UUID ticketId) {
		return null;
	}

	public List<Ticket> fetchAllOpenedTicket(final TicketType actualTicketType, final String latitude, final String longitude) {
		return new ArrayList<>();
	}

	public Ticket updateWithFeed(final Feed newFeed) {
		return null;
	}
}

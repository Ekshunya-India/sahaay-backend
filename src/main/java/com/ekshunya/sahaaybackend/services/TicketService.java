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
		//TODO put inside a Java Fiber. If 16 SDK does not work out then we can put this inside a Kotlin Global Async
		//TODO do a pagination using bucket pattern in mongo DB.
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-1
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-2
		return new ArrayList<>();
	}

	public Ticket updateWithFeed(final Feed newFeed) {
		//TODO put inside a Java Fiber the call to MongoDB.
		return null;
	}

	public boolean deleteTicket(final UUID ticketIdToDelete) {

		//TODO put inside a Java Fiber the call to MongoDB.
		return false;
	}

	public List<Ticket> fetchAllOpenTicketsForUser(final UUID userId) {
		//TODO put inside a Java Fiber the call to MongoDB.
		return null;
	}
}

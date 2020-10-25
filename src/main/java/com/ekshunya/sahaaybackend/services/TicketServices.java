package com.ekshunya.sahaaybackend.services;

import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketUpdateDto;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	public TicketDto fetchTicketFromId(final UUID ticketId) {
		//TODO get the TicketDto from MongoDB
		return null;
	}

	public List<TicketDto> fetchAllTicketOfType(final String ticketType, final String latitude, final String longitude) {
		//TODO do a pagination using bucket pattern in mongo DB.
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-1
		// https://www.mongodb.com/blog/post/paging-with-the-bucket-pattern--part-2
		return new ArrayList<>();
	}

	public TicketDto updateTicketWithUpdate(final TicketUpdateDto ticketUpdateDto) {
		//TODO figure out how to retrive the formData
		return null;
	}

	public boolean deleteTicketWithId(final String ticketId) {
		//TODO delete a Ticket in MongoDB
		return false;
	}
}

package com.ekshunya.sahaaybackend.services;

import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class TicketService {
	private final MongoDatabase mongoDatabase;

	@Inject
	public TicketService(final MongoDatabase mongoDatabase){
		this.mongoDatabase = mongoDatabase;
	}
}

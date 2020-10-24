package com.ekshunya.sahaaybackend.services;

import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class TicketServices {
	private final MongoDatabase mongoDatabase;

	@Inject
	public TicketServices(final MongoDatabase mongoDatabase){
		this.mongoDatabase = mongoDatabase;
	}

}

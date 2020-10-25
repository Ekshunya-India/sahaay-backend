package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.services.TicketService;
import com.google.inject.AbstractModule;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class TicketServiceModule extends AbstractModule {
	public TicketService providesTicketService(final MongoDatabase mongoDatabase) {
		return new TicketService(mongoDatabase);
	}
}

package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.services.TicketService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClientSettings;

public class TicketServiceModule extends AbstractModule {
	@Provides
	public TicketService providesTicketService(final MongoClientSettings mongoClientSettings) {
		return new TicketService(mongoClientSettings);
	}
}

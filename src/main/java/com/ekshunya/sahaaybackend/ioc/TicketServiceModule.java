package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.reactivestreams.client.MongoDatabase;

public class TicketServiceModule extends AbstractModule {
	@Provides
	public TicketFacade providesTicketService(final MongoDatabase mongoDatabase){
		return new TicketFacade(mongoDatabase);
	}
}

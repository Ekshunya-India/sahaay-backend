package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.ekshunya.sahaaybackend.services.TicketService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class TicketFacadeModule extends AbstractModule {
	@Provides
	public TicketFacade providesTicketService(final TicketService ticketService){
		return new TicketFacade(ticketService);
	}
}

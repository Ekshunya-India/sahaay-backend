package com.ekshunya.sahaaybackend.ioc;

import com.ekshunya.sahaaybackend.mapper.MainMapper;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.ekshunya.sahaaybackend.services.TicketService;
import com.ekshunya.sahaaybackend.services.multipart.AttachmentProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class TicketFacadeModule extends AbstractModule {
	@Provides
	public TicketFacade providesTicketService(final TicketService ticketService, final MainMapper mainMapper, final AttachmentProcessor attachmentProcessor){
		return new TicketFacade(ticketService, mainMapper, attachmentProcessor);
	}
}

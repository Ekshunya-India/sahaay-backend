package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketTicketIdDeleteHandler implements LightHttpHandler {
    private final TicketFacade ticketFacade;

    @Inject
    public TicketTicketIdDeleteHandler(final TicketFacade ticketFacade){
        this.ticketFacade = ticketFacade;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String ticketInString = "";
        try {
            ticketInString = exchange.getPathParameters().get("ticketId").getFirst();
            UUID ticketId = UUID.fromString(ticketInString);
            boolean ticketDeleted = this.ticketFacade.deleteTicketWithIdForUserId(ticketId, UUID.randomUUID()); //TODO add logic to get the userId from the Authenticated JWT token.
            if (ticketDeleted) {
                exchange.setStatusCode(200);
            }
        } catch (DataNotFoundException dataNotFoundException) {
            log.error("There was no Ticket With Id {} in our DB", ticketInString);
            exchange.setStatusCode(404);
        }
        exchange.endExchange();
    }
}

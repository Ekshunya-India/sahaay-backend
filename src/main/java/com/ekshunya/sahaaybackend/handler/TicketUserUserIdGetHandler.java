package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.List;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class TicketUserUserIdGetHandler implements LightHttpHandler {
    private final TicketFacade ticketFacade;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketUserUserIdGetHandler(final TicketFacade ticketFacade,
                                      final ObjectMapper objectMapper){
        this.ticketFacade = ticketFacade;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String userId = exchange.getPathParameters().get("userId").getFirst();
        List<TicketDto> tickets = this.ticketFacade.fetchAllTicketsForUser(userId);
        exchange.setStatusCode(200);
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(tickets));
        exchange.endExchange();
    }
}

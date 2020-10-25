package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.UUID;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class TicketTicketIdGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final TicketServices ticketServices;

    @Inject
    public TicketTicketIdGetHandler(final ObjectMapper objectMapper, final TicketServices ticketServices){
        this.objectMapper = objectMapper;
        this.ticketServices = ticketServices;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        UUID ticketId = UUID.fromString(exchange.getPathParameters().get("ticketId").getFirst());
        TicketDto ticketDto = this.ticketServices.fetchTicketFromId(ticketId); //TODO add a try catch to catch Nothing Found exception
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(ticketDto));
        exchange.setStatusCode(200);
        exchange.endExchange();
    }
}

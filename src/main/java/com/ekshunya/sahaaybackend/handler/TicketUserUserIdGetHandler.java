package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class TicketUserUserIdGetHandler implements LightHttpHandler {
    private final TicketServices ticketServices;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketUserUserIdGetHandler(final TicketServices ticketServices,
                                      final ObjectMapper objectMapper){
        this.ticketServices = ticketServices;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String userId = exchange.getPathParameters().get("userId").getFirst();
        List<TicketDto> tickets = this.ticketServices.fetchAllTicketsForUser(userId);
        exchange.setStatusCode(200);
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(tickets));
        exchange.endExchange();
    }
}

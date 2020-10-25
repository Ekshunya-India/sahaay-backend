package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketTicketIdDeleteHandler implements LightHttpHandler {
    private final TicketServices ticketServices;

    @Inject
    public TicketTicketIdDeleteHandler(final TicketServices ticketServices){
        this.ticketServices = ticketServices;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String ticketId="";
        try{
            ticketId = exchange.getPathParameters().get("ticketId").getFirst();
            boolean ticketDeleted = this.ticketServices.deleteTicketWithId(ticketId);
            if(ticketDeleted){
                exchange.setStatusCode(200);
            }} catch (DataNotFoundException dataNotFoundException){
            log.error("There was no Ticket With Id {} in our DB",ticketId);
            exchange.setStatusCode(404);
        }
        exchange.endExchange();
    }
}

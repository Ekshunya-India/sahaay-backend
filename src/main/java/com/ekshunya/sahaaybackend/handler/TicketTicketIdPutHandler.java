package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketTicketIdPutHandler implements LightHttpHandler {

    private final ObjectMapper objectMapper;
    private final TicketServices ticketServices;

    @Inject
    public TicketTicketIdPutHandler(final ObjectMapper objectMapper,
                                     final TicketServices ticketServices){
        this.ticketServices = ticketServices;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((httpServerExchange, bytes) ->{
            try {
                TicketDetailsUpdateDto ticketDetailsUpdateDto = objectMapper.readValue(bytes,TicketDetailsUpdateDto.class);
                TicketDto updatedTicket = this.ticketServices.updateTicket(ticketDetailsUpdateDto);
                httpServerExchange.getResponseSender().send(this.objectMapper.writeValueAsString(updatedTicket));
                httpServerExchange.setStatusCode(200);
            } catch (IOException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                httpServerExchange.setStatusCode(400);
            }
        });
        exchange.endExchange();
    }
}

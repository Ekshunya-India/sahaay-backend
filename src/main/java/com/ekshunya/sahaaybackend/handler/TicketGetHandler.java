package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final TicketServices ticketServices;

    @Inject
    public TicketGetHandler(final ObjectMapper objectMapper, final TicketServices ticketServices){
        this.objectMapper = objectMapper;
        this.ticketServices = ticketServices;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        //This Makes the call async non blocking. We will further use mongo db stream to call the DB in an ASYNC way.
        exchange.getRequestReceiver().receiveFullBytes(
                (httpServerExchange, bytes) -> {
                    //TODO add code here to call the Mongo DB.
                    try {
                        TicketCreateDto ticketCreateDto = this.objectMapper.readValue(bytes, TicketCreateDto.class);
                        TicketDto ticketDto = this.ticketServices.createTicket(ticketCreateDto);
                        httpServerExchange.getResponseSender().send(this.objectMapper.writeValueAsString(ticketDto));
                        httpServerExchange.setStatusCode(200);
                    } catch (IOException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                        httpServerExchange.setStatusCode(400);
                    }
                    httpServerExchange.endExchange();
                }
        );
    }
}

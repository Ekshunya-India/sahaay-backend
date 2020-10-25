package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.model.dtos.TicketDetailsUpdateDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
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
public class TicketTicketIdPutHandler implements LightHttpHandler {

    private final ObjectMapper objectMapper;
    private final TicketFacade ticketFacade;

    @Inject
    public TicketTicketIdPutHandler(final ObjectMapper objectMapper,
                                     final TicketFacade ticketFacade){
        this.ticketFacade = ticketFacade;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((httpServerExchange, bytes) ->{
            try {
                TicketDetailsUpdateDto ticketDetailsUpdateDto = objectMapper.readValue(bytes,TicketDetailsUpdateDto.class);
                TicketDto updatedTicket = this.ticketFacade.updateTicket(ticketDetailsUpdateDto);
                httpServerExchange.getResponseSender().send(this.objectMapper.writeValueAsString(updatedTicket));
                httpServerExchange.setStatusCode(200);
            } catch (IOException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                httpServerExchange.setStatusCode(400);
            } catch (DataNotFoundException dataNotFoundException){
                log.error(Arrays.toString(dataNotFoundException.getStackTrace()));
                httpServerExchange.setStatusCode(404);
            }
        });
        exchange.endExchange();
    }
}

package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TicketTicketIdGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final TicketFacade ticketFacade;

    @Inject
    public TicketTicketIdGetHandler(final ObjectMapper objectMapper, final TicketFacade ticketFacade){
        this.objectMapper = objectMapper;
        this.ticketFacade = ticketFacade;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        try{
            UUID ticketId = UUID.fromString(exchange.getPathParameters().get("ticketId").getFirst());
            TicketDto ticketDto = this.ticketFacade.fetchTicketFromId(ticketId);
            exchange.getResponseSender().send(this.objectMapper.writeValueAsString(ticketDto));
            exchange.setStatusCode(200);
        } catch (final DataNotFoundException dataNotFoundException){
            log.error("There was no ticket with the Id specified in the Query Parameters");
            exchange.setStatusCode(404);

        } catch (final IllegalArgumentException illegalArgumentException){
            log.error("The ticketId provided was not a valid UUID");
            exchange.setStatusCode(400);
        }

        exchange.endExchange();
    }
}

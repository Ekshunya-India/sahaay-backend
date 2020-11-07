package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataNotFoundException;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final TicketFacade ticketFacade;

    @Inject
    public TicketGetHandler(final ObjectMapper objectMapper, final TicketFacade ticketFacade){
        this.objectMapper = objectMapper;
        this.ticketFacade = ticketFacade;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws InterruptedException {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        //This Makes the call async non blocking. We will further use mongo db stream to call the DB in an ASYNC way.
        try {
            Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
            String ticketType  = queryParameters.get("ticketType").getFirst();
            String latitude = queryParameters.get("latitude").getFirst();
            String longitude = queryParameters.get("longitude").getFirst();
            List<TicketDto> ticketDtos = this.ticketFacade.fetchAllTicketOfType(ticketType,latitude,longitude);
            exchange.getResponseSender().send(this.objectMapper.writeValueAsString(ticketDtos));
            exchange.setStatusCode(200);
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            exchange.setStatusCode(400);
        } catch (DataNotFoundException dataNotFoundException) {
            log.error(Arrays.toString(dataNotFoundException.getStackTrace()));
            exchange.setStatusCode(404);
        }
        exchange.endExchange();
    }
}

package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketUpdateDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketUpdatePostHandler implements LightHttpHandler {
    private final TicketServices ticketServices;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketUpdatePostHandler(final TicketServices ticketServices,
                                   final ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.ticketServices = ticketServices;
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        String ticketId = exchange.getPathParameters().get("ticketId").getFirst();
        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        TicketUpdateDto ticketUpdateDto = new TicketUpdateDto(ticketId, formData);
        TicketDto updatedTicket = this.ticketServices.updateTicketWithUpdate(ticketUpdateDto);
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(updatedTicket));
        exchange.setStatusCode(200);
        exchange.endExchange();
    }
}

package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketUpdateDto;
import com.ekshunya.sahaaybackend.services.TicketServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
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
    private final ObjectMapper objectMapper;
    private final TicketServices ticketServices;

    @Inject
    public TicketUpdatePostHandler(final ObjectMapper objectMapper,
                                   final TicketServices ticketServices){
        this.ticketServices = ticketServices;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        String ticketIdInString= exchange.getPathParameters().get("ticketId").getFirst();
        UUID ticketId = UUID.fromString(ticketIdInString);
        exchange.getRequestReceiver().receiveFullBytes(
                (httpServerExchange, bytes)->{
                    try { //TODO This needs to be changed. We need to see the best way to upload big files to undertow.
                        TicketUpdateDto ticketUpdateDto = this.objectMapper.readValue(bytes,TicketUpdateDto.class);
                    } catch (IOException e) {
                        log.error("THis is temprevery. Great Spelling");
                    }
                }
        );
        exchange.endExchange();
    }
}

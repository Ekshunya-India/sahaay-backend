package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.io.Receiver;
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
public class TicketGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;

    @Inject
    public TicketGetHandler(final ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        //This Makes the call async non blocking. We will further use mongo db stream to call the DB in an ASYNC way.
        exchange.getRequestReceiver().receiveFullBytes(
                (httpServerExchange, bytes) -> {
                    //TODO add code here to call the Mongo DB.
                    try {
                        TicketCreateDto ticketCreateDto = this.objectMapper.readValue(bytes, TicketCreateDto.class);

                    } catch (IOException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                        httpServerExchange.setStatusCode(400);
                    }
                    httpServerExchange.endExchange();
                }
        );
    }
}

package com.ekshunya.sahaaybackend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/ //TODO this is yet to be wired up to the ticket Facade.
public class TicketAssignedToUserIdGetHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final MongoDatabase mongoDatabase;

    @Inject
    public TicketAssignedToUserIdGetHandler(final ObjectMapper objectMapper,
                                            final MongoDatabase mongoDatabase){
        this.mongoDatabase = mongoDatabase;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        exchange.endExchange();
    }
}

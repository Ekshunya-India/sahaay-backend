package com.ekshunya.sahaaybackend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class UserPhoneAddPostHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final MongoDatabase mongoDatabase;

    @Inject
    public UserPhoneAddPostHandler(final ObjectMapper objectMapper,
                                      final MongoDatabase mongoDatabase){
        this.mongoDatabase = mongoDatabase;
        this.objectMapper = objectMapper;
    }
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.endExchange();
    }
}

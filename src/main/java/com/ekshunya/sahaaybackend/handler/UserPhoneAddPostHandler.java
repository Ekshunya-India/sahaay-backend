package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.PhoneNumberAddDto;
import com.ekshunya.sahaaybackend.model.dtos.UserDto;
import com.ekshunya.sahaaybackend.services.UserService;
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
public class UserPhoneAddPostHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Inject
    public UserPhoneAddPostHandler(final ObjectMapper objectMapper,
                                   final UserService userService) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getRequestReceiver().receiveFullBytes((httpServerExchange, bytes)->{
            try {
                PhoneNumberAddDto phoneNumberAddDto = this.objectMapper.readValue(bytes,PhoneNumberAddDto.class );
                UserDto createdUser = userService.addPhoneNumber(phoneNumberAddDto);
                httpServerExchange.setStatusCode(200);
                httpServerExchange.getResponseSender().send(this.objectMapper.writeValueAsString(createdUser));
            } catch (IOException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                httpServerExchange.setStatusCode(400);
            }
        });
        exchange.endExchange();
    }
}

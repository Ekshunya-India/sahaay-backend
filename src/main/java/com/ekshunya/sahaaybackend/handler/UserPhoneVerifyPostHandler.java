package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.PhoneNumberVerifyDto;
import com.ekshunya.sahaaybackend.services.UserService;
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
public class UserPhoneVerifyPostHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Inject
    public UserPhoneVerifyPostHandler(final ObjectMapper objectMapper,
                                   final UserService userService){
        this.userService=userService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes((httpServerExchange, bytes)->{
            try {
                PhoneNumberVerifyDto phoneNumberVerifyDto = this.objectMapper.readValue(bytes, PhoneNumberVerifyDto.class);
                boolean userVerified = userService.verifyPhoneNumber(phoneNumberVerifyDto);
                if(!userVerified){
                    httpServerExchange.setStatusCode(403);
                } else {
                    httpServerExchange.setStatusCode(200);
                }
            } catch (IOException e) {
                log.error(Arrays.toString(e.getStackTrace()));
                httpServerExchange.setStatusCode(400);
            }
        });
        exchange.endExchange();
    }
}
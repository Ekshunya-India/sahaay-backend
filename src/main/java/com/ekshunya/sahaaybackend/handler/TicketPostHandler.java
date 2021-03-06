package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.DataAlreadyExistsException;
import com.ekshunya.sahaaybackend.exceptions.InternalServerException;
import com.ekshunya.sahaaybackend.model.dtos.TicketCreateDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketPostHandler implements LightHttpHandler {
    private final ObjectMapper objectMapper;
    private final TicketFacade ticketFacade;

    @Inject
    public TicketPostHandler(final ObjectMapper objectMapper,
                             final TicketFacade ticketFacade){
        this.ticketFacade = ticketFacade;
        this.objectMapper = objectMapper;
    }

    //TODO currently i am not sure if all the code of ticketFacade is in the main thread. It will be better if we move this to the worker thread of undertow by using exchange.someFUnctionTOCallTOWorkerThread(Our code to call the facade and get the results.)
    //TODO We need to think about the userType and the corresponding API privlages they have.l Eg: Partners cannot suddenly start deleting tickets which they have not created. Or Update Ticket Details in such a way to derail the conversation etc.
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullBytes(
                (httpServerExchange, bytes) -> {
                    try {
                        TicketCreateDto ticketCreateDto = this.objectMapper.readValue(bytes, TicketCreateDto.class);
                        UUID idOfTheSavedResource =this.ticketFacade.createTicket(ticketCreateDto);
                            String requestUrl = exchange.getHostAndPort();
                            httpServerExchange.getResponseHeaders().add(HttpString.tryFromString("Location"),requestUrl+"/ticket/"+idOfTheSavedResource);
                            httpServerExchange.setStatusCode(201);
                    } catch (IOException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                        httpServerExchange.setStatusCode(400);
                    } catch (DataAlreadyExistsException dataAlreadyExistsException) {
                        log.error(Arrays.toString(dataAlreadyExistsException.getStackTrace()));
                        httpServerExchange.setStatusCode(409);
                    } catch (InterruptedException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                        throw new InternalServerException("There was an error while processing the request");
                    }
                    httpServerExchange.endExchange();
                }
        );
        exchange.endExchange();
    }
}

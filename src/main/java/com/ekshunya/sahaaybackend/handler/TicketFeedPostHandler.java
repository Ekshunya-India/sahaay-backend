package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import lombok.extern.slf4j.Slf4j;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
@Slf4j
public class TicketFeedPostHandler implements LightHttpHandler {
    private final TicketFacade ticketFacade;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketFeedPostHandler(final TicketFacade ticketFacade,
                                 final ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.ticketFacade = ticketFacade;
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        String ticketId = exchange.getPathParameters().get("ticketId").getFirst();
        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        TicketFeedDto ticketFeedDto = new TicketFeedDto(ticketId, formData);
        TicketDto updatedTicket = this.ticketFacade.updateTicketWithFeed(ticketFeedDto);
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(updatedTicket));
        exchange.setStatusCode(200);
        exchange.endExchange();
    }
}
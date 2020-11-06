package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.model.dtos.TicketFeedDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import com.networknt.httpstring.AttachmentConstants;
import com.networknt.utility.Constants;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

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
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        String ticketId = exchange.getPathParameters().get("ticketId").getFirst();
        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        Map<String, Object> auditInfo = exchange.getAttachment(AttachmentConstants.AUDIT_INFO);
        String userId = auditInfo.get(Constants.USER_ID_STRING).toString();  //TODO this is not be valid. accroding to the excamplke this called_id seems to be the actual constant where the called id is called. https://github.com/networknt/light-4j/blob/master/metrics/src/main/java/com/networknt/metrics/MetricsHandler.java#L131
        TicketFeedDto ticketFeedDto = new TicketFeedDto(UUID.fromString(ticketId), formData, UUID.fromString(userId)); //TODO get the user Id from the Authenticated Manager from one of the middleware                 https://github.com/networknt/light-4j/blob/b2aa4356b87b53007e063c2d11da6182a8ba8685/metrics/src/main/java/com/networknt/metrics/MetricsHandler.java#L131
        boolean updatedTicket = this.ticketFacade.updateTicketWithFeed(ticketFeedDto);
        exchange.getResponseSender().send(Boolean.toString(updatedTicket));
        exchange.setStatusCode(200); //TODO add an if condition to set other status if the value is something else.
        exchange.endExchange();
    }
}
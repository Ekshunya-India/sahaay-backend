package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.model.daos.UserType;
import com.ekshunya.sahaaybackend.model.dtos.TicketDto;
import com.ekshunya.sahaaybackend.services.TicketFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.networknt.handler.LightHttpHandler;
import com.networknt.httpstring.AttachmentConstants;
import com.networknt.utility.Constants;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * For more information on how to write business handlers, please check the link below.
 * https://doc.networknt.com/development/business-handler/rest/
 */
@Slf4j
public class TicketUserUserIdGetHandler implements LightHttpHandler {
    private final TicketFacade ticketFacade;
    private final ObjectMapper objectMapper;

    @Inject
    public TicketUserUserIdGetHandler(final TicketFacade ticketFacade,
                                      final ObjectMapper objectMapper) {
        this.ticketFacade = ticketFacade;
        this.objectMapper = objectMapper;
    }
//TODO Lets implement pagination with the help of a filter like this {sorted_field : {$gt : <value from last record>}}
// the first call from UI will be like getMeTheFirst20TicketDto's and the the second call would be like getMeThe20TicketDtosWhereSorted_Field :$gt : <value from last record>

    //TODO there is a need of Integration test for all these pagination calls.
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(this);
            return;
        }
        Map<String, Object> auditInfo = exchange.getAttachment(AttachmentConstants.AUDIT_INFO);
        String loggedInUserId = auditInfo.get(Constants.USER_ID_STRING).toString();  //TODO this is not be valid. accroding to the excamplke this called_id seems to be the actual constant where the called id is called. https://github.com/networknt/light-4j/blob/master/metrics/src/main/java/com/networknt/metrics/MetricsHandler.java#L131
        String loggedInUserType = auditInfo.get(Constants.USER_TYPE_STRING).toString();
        String ticketOpenedUserId = exchange.getPathParameters().get("userId").getFirst();
        String sortBy = HandlerHelper.fetchSortByFrom(exchange);
        String lastValueOfLastSearch = HandlerHelper.fetchLastDisplayElement(exchange);
        String limitValuesTo = HandlerHelper.fetchLimitFrom(exchange);
        if (loggedInUserType.equals(UserType.CUSTOMER.name()) || loggedInUserType.equals(UserType.PARTNER.name())) {
            if (loggedInUserId.equals(ticketOpenedUserId)) {
                throw new IllegalAccessException("This user is not authorized to access data of other User");
            }
        }
        List<TicketDto> tickets = this.ticketFacade.fetchAllTicketsPaginatedForUser(ticketOpenedUserId, sortBy, lastValueOfLastSearch, limitValuesTo);
        exchange.setStatusCode(200);
        exchange.getResponseSender().send(this.objectMapper.writeValueAsString(tickets));
        exchange.endExchange();
    }
}

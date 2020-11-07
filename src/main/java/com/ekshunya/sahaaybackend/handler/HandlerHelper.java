package com.ekshunya.sahaaybackend.handler;

import com.ekshunya.sahaaybackend.exceptions.BadDataException;
import io.undertow.server.HttpServerExchange;
import lombok.NonNull;
import org.apache.commons.collections.CollectionUtils;

import java.util.Deque;

public class HandlerHelper {
    public static String fetchSortByFrom(@NonNull final HttpServerExchange exchange){
        Deque<String> sortBy = exchange.getQueryParameters().get("sortby");
        if(CollectionUtils.isEmpty(sortBy)){
            throw new BadDataException("Sort By is needed as a parameter for fetching all tickets");
        }
        return sortBy.getFirst();
    }

    public static String fetchLastDisplayElement(@NonNull final HttpServerExchange exchange){
        return exchange.getQueryParameters().get("last") == null ?
                "NO_VALUE" : exchange.getQueryParameters().get("last").getFirst();
    }

    public static String fetchLimitFrom(@NonNull final HttpServerExchange exchange){
        return exchange.getQueryParameters().get("limit") == null ?
                "NO_VALUE" : exchange.getQueryParameters().get("limit").getFirst();
    }
}

package com.ekshunya.sahaaybackend.model.dtos;

import java.time.ZonedDateTime;
import java.util.List;

public class TicketDto  {
    LocationDto locationDto;
    ZonedDateTime expectedEnd;
    Integer visibility;
    String description;
    String ticketType;
    String title;
    String priority;
    String userId;
    List<String> tags;
}

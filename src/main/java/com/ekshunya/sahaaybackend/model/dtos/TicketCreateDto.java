package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class TicketCreateDto  {
    LocationDto locationDto;
    ZonedDateTime expectedEnd;
    Integer visibility;
    String description;
    String ticketType;
    String title;
    String priority;
    String userId;
}

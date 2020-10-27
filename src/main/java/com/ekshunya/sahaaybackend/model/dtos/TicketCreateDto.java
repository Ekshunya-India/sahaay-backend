package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class TicketCreateDto  {
    LocationDto location;
    String expectedEnd;
    int visibility;
    String desc;
    String ticketType;
    String title;
    String priority;
    String userId;
}

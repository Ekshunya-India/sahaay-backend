package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

import java.util.UUID;

@Value
public class TicketCreateDto  {
    UUID id;
    LocationDto location;
    String expectedEnd;
    int visibility;
    String desc;
    String ticketType;
    String title;
    String priority;
    String userId;
}

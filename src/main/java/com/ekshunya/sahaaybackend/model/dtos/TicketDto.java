package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
public class TicketDto  {
    LocationDto location;
    String expectedEnd;
    int visibility;
    String desc;
    String ticketType;
    String title;
    String priority;
    String userId;
    List<String> tags;
    String state;
}

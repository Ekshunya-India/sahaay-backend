package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

@Value
public class TicketDetailsUpdateDto  {
    LocationDto location;
    String expectedEnd;
    Integer visibility;
    String ticketAssignedTo;
    String actualEnd;
    String desc;
    String ticketType;
    String title;
    String priority;
    String currentState;
}

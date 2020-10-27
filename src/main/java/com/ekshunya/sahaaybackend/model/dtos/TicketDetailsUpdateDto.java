package com.ekshunya.sahaaybackend.model.dtos;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TicketDetailsUpdateDto  {
    LocationDto locationDto;
    List<CommentDto> comments;
    ZonedDateTime expectedEnd;
    Integer visibility;
    String ticketAssignedTo;
    ZonedDateTime actualEnd;
    String description;
    String ticketType;
    String title;
    String priority;
    String currentState;
}

package com.ekshunya.sahaaybackend.model.dtos;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TicketDetailsUpdateDto  {
    LocationDto location;
    List<CommentDto> comments;
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

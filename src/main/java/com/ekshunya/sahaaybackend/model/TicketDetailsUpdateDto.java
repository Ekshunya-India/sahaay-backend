package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDetailsUpdateDto  {

    private LocationDto locationDto;
    private java.util.List<CommentDto> comments;
    private java.time.LocalDateTime expectedEnd;
    private Integer visibility;
    private UserDto ticketAssignedTo;
    private java.time.LocalDateTime actualEnd;
    private String description;
    private String ticketType;
    private String title;
    private String priority;
    
    
    public enum CurrentStateEnum {
        
        CANCELLED ("CANCELLED"), 
        
        CLOSED ("CLOSED"), 
        
        CAMPAIGNING ("CAMPAIGNING"), 
        
        VOTE_OF_CONFIDENCE ("VOTE_OF_CONFIDENCE"), 
        
        OPENED ("OPENED"); 
        

        private final String value;

        CurrentStateEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static CurrentStateEnum fromValue(String text) {
            for (CurrentStateEnum b : CurrentStateEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                return b;
                }
            }
            return null;
        }
    }

    private CurrentStateEnum currentState;

    
    private java.util.List<UpdateDto> updates;

    public TicketDetailsUpdateDto () {
    }

    @JsonProperty("locationDto")
    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
    }

    @JsonProperty("comments")
    public java.util.List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(java.util.List<CommentDto> comments) {
        this.comments = comments;
    }

    @JsonProperty("expectedEnd")
    public java.time.LocalDateTime getExpectedEnd() {
        return expectedEnd;
    }

    public void setExpectedEnd(java.time.LocalDateTime expectedEnd) {
        this.expectedEnd = expectedEnd;
    }

    @JsonProperty("visibility")
    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("ticketAssignedTo")
    public UserDto getTicketAssignedTo() {
        return ticketAssignedTo;
    }

    public void setTicketAssignedTo(UserDto ticketAssignedTo) {
        this.ticketAssignedTo = ticketAssignedTo;
    }

    @JsonProperty("actualEnd")
    public java.time.LocalDateTime getActualEnd() {
        return actualEnd;
    }

    public void setActualEnd(java.time.LocalDateTime actualEnd) {
        this.actualEnd = actualEnd;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("ticketType")
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("priority")
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @JsonProperty("currentState")
    public CurrentStateEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentStateEnum currentState) {
        this.currentState = currentState;
    }

    @JsonProperty("updates")
    public java.util.List<UpdateDto> getUpdates() {
        return updates;
    }

    public void setUpdates(java.util.List<UpdateDto> updates) {
        this.updates = updates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketDetailsUpdateDto TicketDetailsUpdateDto = (TicketDetailsUpdateDto) o;

        return Objects.equals(locationDto, TicketDetailsUpdateDto.locationDto) &&
               Objects.equals(comments, TicketDetailsUpdateDto.comments) &&
               Objects.equals(expectedEnd, TicketDetailsUpdateDto.expectedEnd) &&
               Objects.equals(visibility, TicketDetailsUpdateDto.visibility) &&
               Objects.equals(ticketAssignedTo, TicketDetailsUpdateDto.ticketAssignedTo) &&
               Objects.equals(actualEnd, TicketDetailsUpdateDto.actualEnd) &&
               Objects.equals(description, TicketDetailsUpdateDto.description) &&
               Objects.equals(ticketType, TicketDetailsUpdateDto.ticketType) &&
               Objects.equals(title, TicketDetailsUpdateDto.title) &&
               Objects.equals(priority, TicketDetailsUpdateDto.priority) &&
               Objects.equals(currentState, TicketDetailsUpdateDto.currentState) &&
               Objects.equals(updates, TicketDetailsUpdateDto.updates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationDto, comments, expectedEnd, visibility, ticketAssignedTo, actualEnd, description, ticketType, title, priority, currentState, updates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TicketDetailsUpdateDto {\n");
        sb.append("    locationDto: ").append(toIndentedString(locationDto)).append("\n");        sb.append("    comments: ").append(toIndentedString(comments)).append("\n");        sb.append("    expectedEnd: ").append(toIndentedString(expectedEnd)).append("\n");        sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");        sb.append("    ticketAssignedTo: ").append(toIndentedString(ticketAssignedTo)).append("\n");        sb.append("    actualEnd: ").append(toIndentedString(actualEnd)).append("\n");        sb.append("    description: ").append(toIndentedString(description)).append("\n");        sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");        sb.append("    title: ").append(toIndentedString(title)).append("\n");        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");        sb.append("    currentState: ").append(toIndentedString(currentState)).append("\n");        sb.append("    updates: ").append(toIndentedString(updates)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

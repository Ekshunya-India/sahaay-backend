package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketCreateDto  {

    private LocationDto locationDto;
    private java.time.LocalDateTime expectedEnd;
    private Integer visibility;
    private String description;
    private String ticketType;
    private String title;
    private String priority;
    private String userId;

    public TicketCreateDto () {
    }

    @JsonProperty("locationDto")
    public LocationDto getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(LocationDto locationDto) {
        this.locationDto = locationDto;
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

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketCreateDto TicketCreateDto = (TicketCreateDto) o;

        return Objects.equals(locationDto, TicketCreateDto.locationDto) &&
               Objects.equals(expectedEnd, TicketCreateDto.expectedEnd) &&
               Objects.equals(visibility, TicketCreateDto.visibility) &&
               Objects.equals(description, TicketCreateDto.description) &&
               Objects.equals(ticketType, TicketCreateDto.ticketType) &&
               Objects.equals(title, TicketCreateDto.title) &&
               Objects.equals(priority, TicketCreateDto.priority) &&
               Objects.equals(userId, TicketCreateDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationDto, expectedEnd, visibility, description, ticketType, title, priority, userId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TicketCreateDto {\n");
        sb.append("    locationDto: ").append(toIndentedString(locationDto)).append("\n");        sb.append("    expectedEnd: ").append(toIndentedString(expectedEnd)).append("\n");        sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");        sb.append("    description: ").append(toIndentedString(description)).append("\n");        sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");        sb.append("    title: ").append(toIndentedString(title)).append("\n");        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

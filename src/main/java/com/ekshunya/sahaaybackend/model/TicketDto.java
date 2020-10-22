package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDto  {

    private LocationDto locationDto;
    private java.time.LocalDateTime expectedEnd;
    private Integer visibility;
    private String description;
    private String ticketType;
    private String title;
    private String priority;
    private String userId;
    private java.util.List<String> tags;

    public TicketDto () {
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

    @JsonProperty("tags")
    public java.util.List<String> getTags() {
        return tags;
    }

    public void setTags(java.util.List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketDto TicketDto = (TicketDto) o;

        return Objects.equals(locationDto, TicketDto.locationDto) &&
               Objects.equals(expectedEnd, TicketDto.expectedEnd) &&
               Objects.equals(visibility, TicketDto.visibility) &&
               Objects.equals(description, TicketDto.description) &&
               Objects.equals(ticketType, TicketDto.ticketType) &&
               Objects.equals(title, TicketDto.title) &&
               Objects.equals(priority, TicketDto.priority) &&
               Objects.equals(userId, TicketDto.userId) &&
               Objects.equals(tags, TicketDto.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationDto, expectedEnd, visibility, description, ticketType, title, priority, userId, tags);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TicketDto {\n");
        sb.append("    locationDto: ").append(toIndentedString(locationDto)).append("\n");        sb.append("    expectedEnd: ").append(toIndentedString(expectedEnd)).append("\n");        sb.append("    visibility: ").append(toIndentedString(visibility)).append("\n");        sb.append("    description: ").append(toIndentedString(description)).append("\n");        sb.append("    ticketType: ").append(toIndentedString(ticketType)).append("\n");        sb.append("    title: ").append(toIndentedString(title)).append("\n");        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");        sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

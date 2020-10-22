package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentDto  {

    private String commentId;
    private String comment;
    private String userId;
    private String ticketId;

    public CommentDto () {
    }

    @JsonProperty("commentId")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("ticketId")
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentDto CommentDto = (CommentDto) o;

        return Objects.equals(commentId, CommentDto.commentId) &&
               Objects.equals(comment, CommentDto.comment) &&
               Objects.equals(userId, CommentDto.userId) &&
               Objects.equals(ticketId, CommentDto.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, comment, userId, ticketId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CommentDto {\n");
        sb.append("    commentId: ").append(toIndentedString(commentId)).append("\n");        sb.append("    comment: ").append(toIndentedString(comment)).append("\n");        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");        sb.append("    ticketId: ").append(toIndentedString(ticketId)).append("\n");
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

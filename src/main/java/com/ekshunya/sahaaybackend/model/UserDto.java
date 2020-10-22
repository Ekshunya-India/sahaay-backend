package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto  {

    private String userType;
    private String userName;
    private String userId;
    private String avatarUrl;

    public UserDto () {
    }

    @JsonProperty("userType")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDto UserDto = (UserDto) o;

        return Objects.equals(userType, UserDto.userType) &&
               Objects.equals(userName, UserDto.userName) &&
               Objects.equals(userId, UserDto.userId) &&
               Objects.equals(avatarUrl, UserDto.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType, userName, userId, avatarUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserDto {\n");
        sb.append("    userType: ").append(toIndentedString(userType)).append("\n");        sb.append("    userName: ").append(toIndentedString(userName)).append("\n");        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");        sb.append("    avatarUrl: ").append(toIndentedString(avatarUrl)).append("\n");
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

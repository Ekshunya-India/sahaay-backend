package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumberAddDto  {

    private String userId;
    private String phoneNumber;

    public PhoneNumberAddDto () {
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumberAddDto PhoneNumberAddDto = (PhoneNumberAddDto) o;

        return Objects.equals(userId, PhoneNumberAddDto.userId) &&
               Objects.equals(phoneNumber, PhoneNumberAddDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, phoneNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PhoneNumberAddDto {\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");        sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
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

package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumberVerifyDto  {

    private String userId;
    private String verificationCode;

    public PhoneNumberVerifyDto () {
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("verificationCode")
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumberVerifyDto PhoneNumberVerifyDto = (PhoneNumberVerifyDto) o;

        return Objects.equals(userId, PhoneNumberVerifyDto.userId) &&
               Objects.equals(verificationCode, PhoneNumberVerifyDto.verificationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, verificationCode);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PhoneNumberVerifyDto {\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");        sb.append("    verificationCode: ").append(toIndentedString(verificationCode)).append("\n");
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

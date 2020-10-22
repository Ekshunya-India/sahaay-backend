package com.ekshunya.sahaaybackend.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDto  {

    private String updateId;
    private FormData attachmentList;

    public UpdateDto () {
    }

    @JsonProperty("updateId")
    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    @JsonProperty("attachmentList")
    public FormData getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(FormData attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpdateDto UpdateDto = (UpdateDto) o;

        return Objects.equals(updateId, UpdateDto.updateId) &&
               Objects.equals(attachmentList, UpdateDto.attachmentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateId, attachmentList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateDto {\n");
        sb.append("    updateId: ").append(toIndentedString(updateId)).append("\n");        sb.append("    attachmentList: ").append(toIndentedString(attachmentList)).append("\n");
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

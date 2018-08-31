package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SentMessage {

    @JsonProperty(required = true)
    private boolean ok;

    private Message result;
    private String description;
    private int error_code;

    public boolean isOk() {
        return ok;
    }

    public Message getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public int getError_code() {
        return error_code;
    }

    public long getOffset() {
        return result.getMessage_id();
    }

}

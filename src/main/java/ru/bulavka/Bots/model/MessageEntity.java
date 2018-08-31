package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class MessageEntity {

    @JsonProperty(required = true)
    private Type type;

    @JsonProperty(required = true)
    private int offset;

    @JsonProperty(required = true)
    private int length;

    private String url;
    private User user;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static enum Type {

        MENTION,
        HASHTAG,
        BOT_COMMAND,
        URL,
        EMAIL,
        PHONE_NUMBER,
        BOLD,
        ITALIC,
        CODE,
        PRE,
        TEXT_LINK,
        TEXT_MENTION;

        @JsonCreator
        public static Type forValue(String value) {
            return valueOf(value.toUpperCase());
        }

        @JsonValue
        public String toValue() {
            return toString().toLowerCase();
        }

    }

}
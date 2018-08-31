package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Update {

    @JsonProperty(required = true)
    private int update_id;

    private Message message;
    private Message edited_message;
    private Message channel_post;
    private Message edited_channel_post;

    public int getUpdate_id() {
        return update_id;
    }

    public Message getMessage() {
        return message;
    }

    public Message getEdited_message() {
        return edited_message;
    }

    public Message getChannel_post() {
        return channel_post;
    }

    public Message getEdited_channel_post() {
        return edited_channel_post;
    }

    @Override
    public String toString() {
        return "Update{" + "update_id=" + update_id + ", message=" + message + ", edited_message=" + edited_message + ", channel_post=" + channel_post + ", edited_channel_post=" + edited_channel_post + '}';
    }
}

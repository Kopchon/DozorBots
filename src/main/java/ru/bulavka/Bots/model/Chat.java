package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public class Chat {

    @JsonProperty(required = true)
    private long id;

    @JsonProperty(required = true)
    private Type type;

    private String title;
    private String username;
    private String first_name;
    private String last_name;
    private boolean all_members_are_administrators;
//    private ChatPhoto photo;
    private String description;
    private String invite_link;
//    private Message pinned_message;
    private String sticker_set_name;
    private boolean can_set_sticker_set;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isAll_members_are_administrators() {
        return all_members_are_administrators;
    }

    public void setAll_members_are_administrators(boolean all_members_are_administrators) {
        this.all_members_are_administrators = all_members_are_administrators;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvite_link() {
        return invite_link;
    }

    public void setInvite_link(String invite_link) {
        this.invite_link = invite_link;
    }

    public String getSticker_set_name() {
        return sticker_set_name;
    }

    public void setSticker_set_name(String sticker_set_name) {
        this.sticker_set_name = sticker_set_name;
    }

    public boolean isCan_set_sticker_set() {
        return can_set_sticker_set;
    }

    public void setCan_set_sticker_set(boolean can_set_sticker_set) {
        this.can_set_sticker_set = can_set_sticker_set;
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", type=" + type + ", title='" + title + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public static enum Type {

        PRIVATE,
        GROUP,
        SUPERGROUP,
        CHANNEL;

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

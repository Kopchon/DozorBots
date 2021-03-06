package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty(required = true)
    private int id;

    @JsonProperty(required = true)
    private boolean is_bot;

    @JsonProperty(required = true)
    private String first_name;

    private String last_name;
    private String username;
    private String language_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_bot() {
        return is_bot;
    }

    public void setIs_bot(boolean is_bot) {
        this.is_bot = is_bot;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", is_bot=" + is_bot + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", username='" + username + '\'' + ", language_code='" + language_code + '\'' + '}';
    }
}

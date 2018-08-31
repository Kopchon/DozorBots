package ru.bulavka.Bots.model;

import ru.bulavka.Bots.util.UnixTimestampDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {

    @JsonProperty(required = true)
    private long message_id;

    private User from;

    @JsonProperty(required = true)
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date date;

    @JsonProperty(required = true)
    private Chat chat;

    private User user;
    private Chat forward_from_chat;
    private long forward_from_message_id;
    private String forward_signature;
    @JsonDeserialize(using = UnixTimestampDeserializer.class) private Date forward_date;
    private Message reply_to_message;
    @JsonDeserialize(using = UnixTimestampDeserializer.class) private Date edit_date;
    private String media_group_id;
    private String author_signature;
    private String text;
    private List<MessageEntity> entities;
    private List<MessageEntity> caption_entities;
    private List<PhotoSize> photo;
    private String caption;
    private Location location;

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chat getForward_from_chat() {
        return forward_from_chat;
    }

    public void setForward_from_chat(Chat forward_from_chat) {
        this.forward_from_chat = forward_from_chat;
    }

    public long getForward_from_message_id() {
        return forward_from_message_id;
    }

    public void setForward_from_message_id(long forward_from_message_id) {
        this.forward_from_message_id = forward_from_message_id;
    }

    public String getForward_signature() {
        return forward_signature;
    }

    public void setForward_signature(String forward_signature) {
        this.forward_signature = forward_signature;
    }

    public Date getForward_date() {
        return forward_date;
    }

    public void setForward_date(Date forward_date) {
        this.forward_date = forward_date;
    }

    public Message getReply_to_message() {
        return reply_to_message;
    }

    public void setReply_to_message(Message reply_to_message) {
        this.reply_to_message = reply_to_message;
    }

    public Date getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(Date edit_date) {
        this.edit_date = edit_date;
    }

    public String getMedia_group_id() {
        return media_group_id;
    }

    public void setMedia_group_id(String media_group_id) {
        this.media_group_id = media_group_id;
    }

    public String getAuthor_signature() {
        return author_signature;
    }

    public void setAuthor_signature(String author_signature) {
        this.author_signature = author_signature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MessageEntity> entities) {
        this.entities = entities;
    }

    public List<MessageEntity> getCaption_entities() {
        return caption_entities;
    }

    public void setCaption_entities(List<MessageEntity> caption_entities) {
        this.caption_entities = caption_entities;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getChatId() {
        return getChat().getId();
    }

    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        for (MessageEntity entity : getEntities()) {
            if (entity.getType() == MessageEntity.Type.BOT_COMMAND) {
                commands.add(new Command(text.substring(entity.getOffset(), entity.getOffset() + entity.getLength())));
            }
        }
        return commands;
    }

    public boolean hasText() {
        return text != null && !text.isEmpty();
    }

    public boolean hasPhoto() {
        return photo != null;
    }

    public boolean hasLocation() {
        return location != null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", from=" + from +
                ", date=" + date +
                ", chat=" + chat +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
}

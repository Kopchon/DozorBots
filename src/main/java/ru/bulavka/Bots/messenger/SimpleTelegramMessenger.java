package ru.bulavka.Bots.messenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.bulavka.Bots.model.Location;
import ru.bulavka.Bots.model.SentMessage;
import ru.bulavka.Bots.model.User;
import ru.bulavka.Bots.util.ParseMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@JsonIgnoreProperties({"mapper, url"})
public class SimpleTelegramMessenger implements Messenger {

    private String token;

    @Value("${telegram.url}")
    private String url;

    @Autowired
    private ObjectMapper mapper;

    public SimpleTelegramMessenger(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }

    @JsonIgnore
    @Override
    public User getMe() throws IOException {
        return mapper.readValue(getConnection(Method.getMe).execute().body(), User.class);
    }

    @Override
    public SentMessage sendMessage(String chat_id, String text) throws IOException {
        return parseResponseBody(getConnection(Method.sendMessage)
                .data("chat_id", chat_id)
                .data("text", "<pre>" + text + "</pre>")
                .data("parse_mode", ParseMode.HTML.toString())
                .execute()
                .body());
    }

    @Override
    public SentMessage sendMessage(String chat_id, String text, ParseMode parse_mode) throws IOException {
        return parseResponseBody(getConnection(Method.sendMessage)
                .data("chat_id", chat_id)
                .data("text", text)
                .data("parse_mode", String.valueOf(parse_mode))
                .execute()
                .body());
    }

    @Override
    public SentMessage sendMessage(String chat_id, long reply_to_message_id, String text) throws IOException {
        return parseResponseBody(getConnection(Method.sendMessage)
                .data("chat_id", chat_id)
                .data("text", "<pre>" + text + "</pre>")
                .data("reply_to_message_id", String.valueOf(reply_to_message_id))
                .data("parse_mode", ParseMode.HTML.toString())
                .execute()
                .body());
    }

    @Override
    public SentMessage sendMessage(String chat_id, long reply_to_message_id, String text, ParseMode parse_mode) throws IOException {
        return parseResponseBody(getConnection(Method.sendMessage)
                .data("chat_id", chat_id)
                .data("text", text)
                .data("parse_mode", String.valueOf(parse_mode))
                .data("reply_to_message_id", String.valueOf(reply_to_message_id))
                .execute()
                .body());
    }

    @Override
    public SentMessage sendPhoto(String chat_id, String photo) throws IOException {
        return parseResponseBody(getConnection(Method.sendPhoto)
                .data("chat_id", chat_id)
                .data("photo", photo)
                .execute()
                .body());
    }

    @Override
    public SentMessage sendPhoto(String chat_id, File photo) throws IOException {
        FileInputStream in = new FileInputStream(photo);
        return parseResponseBody(getConnection(Method.sendPhoto)
                .method(Connection.Method.POST)
                .data("chat_id", chat_id)
                .data("photo", photo.getName(), in)
                .execute()
                .body());
    }

    @Override
    public SentMessage sendLocation(String chat_id, Location location) throws IOException {
        return parseResponseBody(getConnection(Method.sendLocation)
                .method(Connection.Method.POST)
                .data("chat_id", chat_id)
                .data("latitude", String.valueOf(location.getLatitude()))
                .data("longitude", String.valueOf(location.getLongitude()))
                .execute()
                .body());
    }

    @Override
    public SentMessage sendLocation(String chat_id, long reply_to_message_id, Location location) throws IOException {
        return parseResponseBody(getConnection(Method.sendPhoto)
                .method(Connection.Method.POST)
                .data("chat_id", chat_id)
                .data("latitude", String.valueOf(location.getLatitude()))
                .data("longitude", String.valueOf(location.getLongitude()))
                .data("reply_to_message_id", String.valueOf(reply_to_message_id))
                .execute()
                .body());
    }

    @JsonIgnore
    private Connection getConnection(Method method) {
        return Jsoup
                .connect(String.format(url + "%s/%s", token, method.toString()))
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .ignoreHttpErrors(true);
    }

    private SentMessage parseResponseBody(String body) throws IOException {
        return mapper.readValue(body, SentMessage.class);
    }

}

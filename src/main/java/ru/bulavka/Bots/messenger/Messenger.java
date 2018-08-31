package ru.bulavka.Bots.messenger;

import ru.bulavka.Bots.model.Location;
import ru.bulavka.Bots.model.SentMessage;
import ru.bulavka.Bots.model.User;
import ru.bulavka.Bots.util.ParseMode;

import java.io.File;
import java.io.IOException;

public interface Messenger {

    User getMe() throws IOException;

    // Send message

    default SentMessage sendMessage(long chat_id, String text) throws IOException {
        return sendMessage(String.valueOf(chat_id), text);
    }

    SentMessage sendMessage(String chat_id, String text) throws IOException;

    default SentMessage sendMessage(long chat_id, String text, ParseMode parse_mode) throws IOException {
        return sendMessage(String.valueOf(chat_id), text, parse_mode);
    }

    SentMessage sendMessage(String chat_id, String text, ParseMode parse_mode) throws IOException;

    default SentMessage sendMessage(long chat_id, long reply_to_message_id, String text) throws IOException {
        return sendMessage(String.valueOf(chat_id), reply_to_message_id, text);
    }

    SentMessage sendMessage(String chat_id, long reply_to_message_id, String text) throws IOException;

    default SentMessage sendMessage(long chat_id, long reply_to_message_id, String text, ParseMode parse_mode) throws IOException {
        return sendMessage(String.valueOf(chat_id), reply_to_message_id, text, parse_mode);
    }

    SentMessage sendMessage(String chat_id, long reply_to_message_id, String text, ParseMode parse_mode) throws IOException;

    // Send photo
    SentMessage sendPhoto(String chat_id, String photo) throws IOException;

    default SentMessage sendPhoto(long chat_id, String photo) throws IOException {
        return sendPhoto(String.valueOf(chat_id), photo);
    }

    SentMessage sendPhoto(String chat_id, File photo) throws IOException;

    default SentMessage sendPhoto(long chat_id, File photo) throws IOException {
        return sendPhoto(String.valueOf(chat_id), photo);
    }

    // Send location

    SentMessage sendLocation(String chat_id, Location location) throws IOException;

    default SentMessage sendLocation(long chat_id, Location location) throws IOException {
        return sendLocation(String.valueOf(chat_id), location);
    }

    SentMessage sendLocation(String chat_id, long reply_to_message_id, Location location) throws IOException;

    default SentMessage sendLocation(long chat_id, long reply_to_message_id, Location location) throws IOException {
        return sendLocation(String.valueOf(chat_id), location);
    }

    enum Method {

        getMe,
        sendMessage,
        sendPhoto,
        sendLocation

    }

}

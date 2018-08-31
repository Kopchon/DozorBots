package ru.olenevody.service;

import org.springframework.stereotype.Service;
import ru.bulavka.Bots.model.Location;

public interface MessageParser {

    TelegramMessageType getMessageType(String text);

    boolean isCode(String text);

    boolean isAdminCommand(String text);

    boolean isGameCommand(String text);

    boolean isCoordinates(String text);

    Location parseCoordinates(String text);

}

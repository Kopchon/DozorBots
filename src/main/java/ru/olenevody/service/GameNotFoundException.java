package ru.olenevody.service;

public class GameNotFoundException extends Exception {

    public GameNotFoundException(Long chat_is) {
        super("In chat " + chat_is + " game not found");
    }

}

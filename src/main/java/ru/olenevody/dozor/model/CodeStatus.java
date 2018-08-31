package ru.olenevody.dozor.model;

public enum CodeStatus {

    NOT_DONE("Код не принят"),
    DONE("Код принят"),
    DONE_BONUS("Бонусный код принят"),
    DONE_AGAIN("Код введен повторно"),
    DONE_AGAIN_BONUS("Бонусный код введен повторно"),
    GAME_OVER("Игра окончена"),
    ERROR("Ошибка");

    private String message;

    CodeStatus(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}

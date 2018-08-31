package ru.olenevody.dozor.model;

import ru.bulavka.Bots.model.User;

import java.time.LocalDateTime;

public class CodeInput {

    private LocalDateTime dateTime = LocalDateTime.now();
    private CodeStatus codeStatus;
    private String code;
    private User user;

    public CodeInput() {
    }

    public CodeInput(CodeStatus codeStatus, String code) {
        this(codeStatus, code, null);
    }

    public CodeInput(CodeStatus codeStatus, String code, User user) {
        this.codeStatus = codeStatus;
        this.code = code;
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CodeStatus getCodeStatus() {
        return codeStatus;
    }

    public String getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDone() {
        return codeStatus == CodeStatus.DONE;
    }

}

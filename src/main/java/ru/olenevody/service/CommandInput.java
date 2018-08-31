package ru.olenevody.service;

import org.springframework.stereotype.Component;

@Component
public class CommandInput {

    private String command;
    private String result;

    public CommandInput() {
    }

    public CommandInput(String command, String result) {
        this.command = command;
        this.result = result;
    }

    public String getCommand() {
        return command;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return result;
    }
}

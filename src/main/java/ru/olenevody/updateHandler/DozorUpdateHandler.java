package ru.olenevody.updateHandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bulavka.Bots.listener.Listener;
import ru.bulavka.Bots.messenger.Messenger;
import ru.bulavka.Bots.model.Message;
import ru.bulavka.Bots.model.Update;
import ru.bulavka.Bots.model.Updates;
import ru.bulavka.Bots.updateHandler.AbstractUpdateHandler;
import ru.olenevody.dozor.model.CodeInput;
import ru.olenevody.service.CommandInput;
import ru.olenevody.service.GamesService;
import ru.olenevody.service.TelegramMessageParser;

import java.io.IOException;
import java.util.Properties;

@Component
public class DozorUpdateHandler extends AbstractUpdateHandler {

    @Autowired
    private GamesService gamesService;

    @Autowired
    private TelegramMessageParser messageParser;

    @Autowired
    Messenger messenger;

    @Autowired
    Properties properties;

    private Logger logger = Logger.getLogger(DozorUpdateHandler.class);

    public DozorUpdateHandler() {
    }

    public DozorUpdateHandler(GamesService gamesService, TelegramMessageParser messageParser) {
        this.gamesService = gamesService;
        this.messageParser = messageParser;
    }

    @Override
    public void handleUpdates(Updates updates, Listener listener) {

        if (!updates.isOk()) {
            return;
        }

        for (Update update : updates.getResult()) {

            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                String text = message.getText();

                try {
                    switch (messageParser.getMessageType(text)) {
                        case ADMIN_COMMAND:
                            CommandInput adminCommandInput = handleCommand(message);
                            if (adminCommandInput != null) {
                                messenger.sendMessage(message.getChatId(), message.getMessage_id(), adminCommandInput.getResult());
                            }
                            break;
                        case GAME_COMMAND:
                            CommandInput commandInput = gamesService.handleCommand(message);
                            if (commandInput != null) {
                                messenger.sendMessage(message.getChatId(), message.getMessage_id(), commandInput.getResult());
                            }
                            break;
                        case CODE:
                            if (gamesService.gameStarted(message.getChat())) {
                                CodeInput codeInput = gamesService.enterCode(message);
                                if (codeInput != null) {
                                    messenger.sendMessage(message.getChatId(), message.getMessage_id(), codeInput.getCodeStatus().toString());
                                    if (codeInput.isDone()) {
                                        messenger.sendMessage(message.getChatId(), gamesService.getCodes(message.getChat()).toString());
                                    }
                                }
                            }
                            break;
                        case LOCATION:
                            messenger.sendLocation(message.getChatId(), messageParser.parseCoordinates(text));
                            break;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    private CommandInput handleCommand(Message message) throws IOException {

        String text = message.getText().trim().toUpperCase();

        if (text.equals(properties.getProperty("telegram.command.me"))) {
            return new CommandInput(message.getText(), message.getFrom().toString());
        } else if ( (text.equals(properties.getProperty("telegram.command.chat")))) {
            return new CommandInput(message.getText(), message.getChat().toString());
        } else if ( (text.equals(properties.getProperty("telegram.command.chat.id")))) {
            return new CommandInput(message.getText(), String.valueOf(message.getChatId()));
        }

        return new CommandInput(message.getText(), "Неизвестная команда");

    }

}

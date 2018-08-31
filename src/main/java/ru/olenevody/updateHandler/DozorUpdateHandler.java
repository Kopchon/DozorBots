package ru.olenevody.updateHandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bulavka.Bots.listener.Listener;
import ru.bulavka.Bots.messenger.Messenger;
import ru.bulavka.Bots.model.Location;
import ru.bulavka.Bots.model.Message;
import ru.bulavka.Bots.model.Update;
import ru.bulavka.Bots.model.Updates;
import ru.bulavka.Bots.updateHandler.AbstractUpdateHandler;
import ru.bulavka.Bots.util.ParseMode;
import ru.olenevody.dozor.model.CodeInput;
import ru.olenevody.service.CommandInput;
import ru.olenevody.service.GamesService;
import ru.olenevody.service.TelegramMessageParser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
public class DozorUpdateHandler extends AbstractUpdateHandler {

    private final GamesService gamesService;

    private final TelegramMessageParser messageParser;

    private Logger logger = Logger.getLogger(DozorUpdateHandler.class);


    @Autowired
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
                            handleCommand(message, listener);
                            break;
                        case GAME_COMMAND:
                            CommandInput commandInput = gamesService.handleCommand(message);
                            sendReplyMessage(message, commandInput.toString());
                            break;
                        case CODE:
                            if (gamesService.gameStarted(message.getChat())) {
                                CodeInput codeInput = gamesService.enterCode(message);
                                if (codeInput != null) {
                                    sendReplyMessage(message, codeInput.getCodeStatus().toString());
                                    if (codeInput.isDone()) {
                                        gamesService.getCodes(message.getChat());
                                    }
                                }
                            }
                            break;
                        case LOCATION:
                            sendLocation(messageParser.parseCoordinates(text));
                            break;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    private void sendMessage(String text) throws IOException {
        Map<Messenger, Set<Long>> chats = getChats();
        for (Map.Entry<Messenger, Set<Long>> entry : chats.entrySet()) {
            for (Long chat_id : entry.getValue()) {
                entry.getKey().sendMessage(chat_id, text, ParseMode.HTML);
            }
        }
    }

    private void sendReplyMessage(Message message, String text) throws IOException {
        Map<Messenger, Set<Long>> chats = getChats();
        for (Map.Entry<Messenger, Set<Long>> entry : chats.entrySet()) {
            for (Long chat_id : entry.getValue()) {
                entry.getKey().sendMessage(chat_id, message.getMessage_id(), text, ParseMode.HTML);
            }
        }
    }

    private void sendLocation(Location location) throws IOException {
        Map<Messenger, Set<Long>> chats = getChats();
        for (Map.Entry<Messenger, Set<Long>> entry : chats.entrySet()) {
            for (Long chat_id : entry.getValue()) {
                entry.getKey().sendLocation(chat_id, location);
            }
        }
    }

}

package ru.bulavka.Bots.updateHandler;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bulavka.Bots.listener.Listener;
import ru.bulavka.Bots.messenger.Messenger;
import ru.bulavka.Bots.model.Message;
import ru.bulavka.Bots.service.MessengerService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractUpdateHandler implements UpdateHandler {

    private String name;

    @Autowired
    private MessengerService messengerService;

    private Map<Messenger, Set<Long>> chats;

    @Autowired
    public AbstractUpdateHandler() {
        this.name = getClass().getSimpleName();
        this.chats = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setChats(Map<Messenger, Set<Long>> chats) {
        this.chats = chats;
    }

    public Map<Messenger, Set<Long>> getChats() {
        return chats;
    }

    public Set<Long> getChats(Messenger messenger) {
        return chats.get(messenger);
    }

    private void addChat(String Token, Long chat_id) {
        Messenger messenger = messengerService.getMessenger(Token);
        Set<Long> chats_id = chats.get(messenger);
        if (chats_id == null) {
            chats_id = new HashSet<>();
            chats.put(messenger, chats_id);
        }
        chats_id.add(chat_id);
    }

    public void handleCommand(Message message, Listener listener) {

        String[] commands = message.getText().trim().replaceAll(" ", " ").split(" ");

        if (commands.length < 2) {
            return;
        }

        for (int i = 1; i < commands.length; i++) {
            switch (commands[i].toUpperCase()) {
                case "ADD" :
                    addChat(listener.getToken(), message.getChatId());
            }
        }

    }


}

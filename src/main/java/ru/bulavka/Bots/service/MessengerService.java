package ru.bulavka.Bots.service;

import ru.olenevody.service.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulavka.Bots.messenger.Messenger;

import java.util.Map;

@Service
public class MessengerService {

    private Map<String, Messenger> messengers;

    @Autowired
    public MessengerService(Map<String, Messenger> messengers) {
        this.messengers = messengers;
    }

    public Messenger getMessenger(String token) {
        Messenger messenger = messengers.get(token);
        if (messenger == null) {
            messenger = BeanUtils.getBean(Messenger.class, token);
            messengers.put(token, messenger);
        }
        return messenger;
    }

    public Messenger addMessenger(String token, Messenger messenger) {
        return messengers.put(token, messenger);
    }

}

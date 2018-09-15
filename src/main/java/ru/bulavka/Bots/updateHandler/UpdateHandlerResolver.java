package ru.bulavka.Bots.updateHandler;

import ru.bulavka.Bots.model.Message;

interface UpdateHandlerResolver {

    UpdateHandler resolve(Message message);

}

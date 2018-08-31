package ru.bulavka.Bots.updateHandler;

import ru.bulavka.Bots.listener.Listener;
import ru.bulavka.Bots.model.Updates;

import java.io.IOException;

public interface UpdateHandler {

    void handleUpdates(Updates updates, Listener listener);

}

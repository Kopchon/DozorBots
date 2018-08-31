package ru.bulavka.Bots.listener;

import ru.bulavka.Bots.model.Updates;

import java.io.IOException;

public interface Listener {

    Updates getUpdates() throws IOException;

    String getToken();

}

package ru.bulavka.Bots.listener;

import ru.bulavka.Bots.model.Updates;

public interface UpdatesFilter {

    Updates filter(Updates updates);

}

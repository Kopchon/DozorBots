package ru.bulavka.Bots.service;

import ru.bulavka.Bots.updateHandler.UpdateHandler;

import java.util.ArrayList;
import java.util.List;

public class UpdateHandlersService {

    private List<UpdateHandler> updateHandlers;

    public UpdateHandlersService() {
        this(new ArrayList<>());
    }

    public UpdateHandlersService(List<UpdateHandler> updateHandlers) {
        this.updateHandlers = updateHandlers;
    }

    public List<UpdateHandler> getUpdatesHandlers() {
        return updateHandlers;
    }

    public UpdateHandler getUpdateHandler(String name) {
        for (UpdateHandler handler : updateHandlers) {
            if (handler.equals(name)) {
                return handler;
            }
        }
        return null;
    }

    public void addUpdateHandler(UpdateHandler handler) {
        updateHandlers.add(handler);
    }

}

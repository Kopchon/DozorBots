package ru.bulavka.Bots.bot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.bulavka.Bots.listener.Listener;
import ru.bulavka.Bots.model.Updates;
import ru.bulavka.Bots.updateHandler.UpdateHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Bot implements Runnable {

    private String name;

    @Value("${telegram.token}")
    private String token;

    @Value("${telegram.delay}")
    private int delay;
    private Status status = Status.CREATED;

    private List<Listener> listeners;
    private List<UpdateHandler> updateHandlers;

    private Logger logger = Logger.getLogger(Bot.class);

    public Bot() {
    }

    public Bot(String token) {
        this("NoName", token);
    }

    public Bot(String name, String token) {
        this(name, token, new ArrayList<>(), new ArrayList<>());
    }

    public Bot(String name, String token, List<Listener> listeners, List<UpdateHandler> updateHandlers) {
        this.name = name;
        this.token = token;
        this.listeners = listeners;
        this.updateHandlers = updateHandlers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public boolean removeListener(Listener listener) {
        return listeners.remove(listener);
    }

    public List<UpdateHandler> getUpdateHandlers() {
        return updateHandlers;
    }

    public void setUpdateHandlers(List<UpdateHandler> updateHandlers) {
        this.updateHandlers = updateHandlers;
    }

    public void addUpdatesHandler(UpdateHandler updateHandler) {
        updateHandlers.add(updateHandler);
    }

    public boolean removeUpdatesHandler(UpdateHandler updateHandler) {
        return updateHandlers.remove(updateHandler);
    }

    public void terminate() {
        status = Status.TERMINATED;
    }

    public void start() throws InterruptedException {
        if (status == Status.RUNNING) {
            throw new InterruptedException(toString() + ": Bot already started");
        }

        if (listeners == null || listeners.isEmpty()) {
            throw new NullPointerException(toString() + ": No listeners is set");
        }
        if (updateHandlers == null || updateHandlers.isEmpty()) {
            throw new NullPointerException(toString() + ": No updateHandlers is set");
        }

        status = Status.RUNNING;
        new Thread(this).start();
    }

    @Override
    public String toString() {
        return name + " (" + token + ")";
    }

    @Override
    public void run() {

        while (status == Status.RUNNING) {

            try {
                Long startTime = System.currentTimeMillis();
                for (Listener listener : listeners) {
                    Updates updates = listener.getUpdates();
                    for (UpdateHandler handler : updateHandlers) {

                        handler.handleUpdates(updates, listener);
                    }
                }
                Long duration = System.currentTimeMillis() - startTime;
                Thread.sleep(duration > delay ? 0 : delay - duration);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                terminate();
            }

        }

    }

    public enum Status {

        CREATED,
        RUNNING,
        TERMINATED

    }

}

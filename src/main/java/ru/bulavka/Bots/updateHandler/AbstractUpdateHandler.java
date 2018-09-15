package ru.bulavka.Bots.updateHandler;

public abstract class AbstractUpdateHandler implements UpdateHandler {

    private String name;

    public AbstractUpdateHandler() {
        this.name = getClass().getSimpleName();
    }

    public String getName() {
        return name;
    }

}

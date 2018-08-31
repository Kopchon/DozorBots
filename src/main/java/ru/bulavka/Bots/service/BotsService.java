package ru.bulavka.Bots.service;

import org.apache.log4j.Logger;
import ru.bulavka.Bots.bot.Bot;
import ru.olenevody.controller.BotsController;

import java.util.ArrayList;
import java.util.List;

public class BotsService {

    private List<Bot> bots;

    static final Logger logger = Logger.getLogger(BotsService.class);

    public BotsService() {
        this(new ArrayList<>());
    }

    public BotsService(List<Bot> bots) {
        this.bots = bots;
    }

    public List<Bot> getBots() {
        return bots;
    }

    public Bot getBot(String token) {
        for (Bot bot : bots) {
            if (bot.getToken().equals(token)) {
                return bot;
            }
        }
        return null;
    }

    public void addBot(Bot bot) {
        if (startBot(bot)) {
            bots.add(bot);
        }

    }

    private boolean startBot(Bot bot) {
        try {
            bot.start();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public void terminateBot(Bot bot) {
        bot.terminate();
    }

    public void startBots() {
        for (Bot bot : bots) {
            startBot(bot);
        }
    }

    public void terminateBots() {
        for (Bot bot : bots) {
            bot.terminate();
        }
    }

}

package ru.olenevody.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bulavka.Bots.bot.Bot;
import ru.bulavka.Bots.service.BotsService;

import java.util.List;

@RestController
public class BotsController {

    private BotsService botsService;

    static final Logger logger = Logger.getLogger(BotsController.class);

    @Autowired
    public BotsController(BotsService botsService) {
        this.botsService = botsService;
    }

    @RequestMapping(value = "/bots", method = RequestMethod.GET)
    public List<Bot> getAllBots() {
        return botsService.getBots();
    }

    @RequestMapping("/bots/{token}")
    public Bot getBot(@PathVariable String token) {
        return botsService.getBot(token);
    }

}

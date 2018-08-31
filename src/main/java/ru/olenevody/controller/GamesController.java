package ru.olenevody.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.bulavka.Bots.model.Chat;
import ru.olenevody.dozor.Game;
import ru.olenevody.service.GamesService;

import java.util.Map;

@RestController
public class GamesController {

    private final GamesService gamesService;

    private Logger logger = Logger.getLogger(BotsController.class);

    @Autowired
    public GamesController(GamesService gamesService) {
        this.gamesService = gamesService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public Map<Chat, Game> bots() {

        return gamesService.getGames();

    }

}

package ru.olenevody.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulavka.Bots.model.Chat;
import ru.bulavka.Bots.model.Message;
import ru.olenevody.dozor.Game;
import ru.olenevody.dozor.model.CodeInput;
import ru.olenevody.dozor.model.Codes;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class GamesService {

    private Map<Chat, Game> games;

    @Autowired
    private Properties properties;

    private Logger logger = Logger.getLogger(GamesService.class);

    public GamesService() {
        games = new HashMap<>();
    }

    public void addGame(String pin, Chat chat) {
        Game game = BeanUtils.getBean(Game.class, pin, chat);
        game.startGame();
        games.put(chat, game);
    }

    public Map<Chat, Game> getGames() {
        return games;
    }

    public void startGames() {
        for (Map.Entry<Chat, Game> entry : games.entrySet()) {
            entry.getValue().startGame();
        }
    }

    public void startGame(Chat chat) throws GameNotFoundException {
        Game game = games.get(chat);
        if (game == null) {
            throw new GameNotFoundException(chat.getId());
        }
        game.startGame();
    }

    public boolean gameStarted(Chat chat) {
         return games.containsKey(chat);
    }

    public CommandInput handleCommand(Message message) {

        String text = message.getText().trim().toUpperCase();
        String[] commands = message.getText().trim().replaceAll(" {2}", " ").split(" ");
        String command = commands[0];
        Game game = games.get(message.getChat());

        CommandInput commandInput = null;
        if (text.startsWith(properties.getProperty("dozor.command.start"))) {
            if (game != null) {
                commandInput = new CommandInput(properties.getProperty("dozor.command.start"), "Игра уже запущена");
                return commandInput;
            }
            if (commands.length < 2) {
                commandInput = new CommandInput(properties.getProperty("dozor.command.start"), "Требуется указать пин игры");
            } else {
                addGame(commands[1], message.getChat());
                String text1 = "Игра запущена, пин = " + commands[1];
                commandInput = new CommandInput(properties.getProperty("dozor.command.start"), text1);
            }
        } else if (text.startsWith(properties.getProperty("dozor.command.pause")) && game != null) {
            game.pauseGame();
            commandInput = new CommandInput(properties.getProperty("dozor.command.pause"), "Игра приостановлена");
        } else if (text.startsWith(properties.getProperty("dozor.command.resume")) && game != null) {
            game.resumeGame();
            commandInput = new CommandInput(properties.getProperty("dozor.command.resume"), "Игра возобновлена");
        } else if (text.startsWith(properties.getProperty("dozor.command.stop")) && game != null) {
            game.stopGame();
            games.remove(message.getChat());
            commandInput = new CommandInput(properties.getProperty("dozor.command.stop"), "Игра остановлена");
        } else if (text.startsWith(properties.getProperty("dozor.command.lvl")) && game != null) {
            return new CommandInput(command, game.getLevel().toString());
        } else if (text.startsWith(properties.getProperty("dozor.command.codes")) && game != null) {
            return new CommandInput(command, game.getCodes().toString());
        }
        return commandInput;
    }

    public CodeInput enterCode(Message message) {
        Game game = games.get(message.getChat());
        if (game != null && game.isRunning()) {
            String code = message.getText();
            // TODO: Если последний, то надо это как-то помечать и не выводить оставшиеся
            return new CodeInput(game.enterCode(code), code, message.getFrom());
        }
        return null;
    }

    public Codes getCodes(Chat chat) {
        Game game = games.get(chat);
        if (game != null) {
            return game.getCodes();
        } else {
            return new Codes();
        }
    }

}

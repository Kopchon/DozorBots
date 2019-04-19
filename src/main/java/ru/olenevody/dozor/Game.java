package ru.olenevody.dozor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.bulavka.Bots.messenger.Messenger;
import ru.bulavka.Bots.model.Chat;
import ru.olenevody.dozor.engine.DozorEngine;
import ru.olenevody.dozor.model.CodeStatus;
import ru.olenevody.dozor.model.Codes;
import ru.olenevody.dozor.model.GameStatus;
import ru.olenevody.dozor.model.Level;

import java.io.IOException;

@Component
@Scope("prototype")
public class Game implements Runnable {

    private String pin;
    private Chat chat;

    @Autowired
    private DozorEngine dozorEngine;

    @Value("${dozor.listener.delay}")
    private int updateLevelDelay;

    @Autowired
    Messenger messenger;

    private Level level = new Level();
    private GameStatus gameStatus = GameStatus.NotStarted;

    private Logger logger = Logger.getLogger(Game.class);

    public Game() {
    }

    public Game(String pin, Chat chat) {
        this.pin = pin;
        this.chat = chat;
    }

    public String getPin() {
        return pin;
    }

    public void startGame() {
        gameStatus = GameStatus.Running;
        dozorEngine.setPin(pin);
        new Thread(this).start();
    }

    public void pauseGame() {
        gameStatus = GameStatus.Suspended;
    }

    public void resumeGame() {
        startGame();
    }

    public void stopGame() {
        gameStatus = GameStatus.Stopped;
    }

    public boolean isRunning() {
        return gameStatus == GameStatus.Running;
    }

    public CodeStatus enterCode(String code) {

        CodeStatus codeStatus;

        try {
            codeStatus = dozorEngine.enterCode(code);
            if (codeStatus == CodeStatus.DONE) {
                updateLevel();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            codeStatus = CodeStatus.ERROR;
        }
        return codeStatus;

    }

    public Codes getCodes() {
        return level.getCodes();
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void run() {

        while (gameStatus == GameStatus.Running) {

            try {

                long startTime = System.currentTimeMillis();

                updateLevel();

                long duration = System.currentTimeMillis() - startTime;
                Thread.sleep(duration > updateLevelDelay ? 0 : updateLevelDelay - duration);

            } catch (Exception e) {
                // Если бот запущен, а игра в движке еще не началась, то метод updateLevel()
                // будет постоянно выбразывать исключения
                if (level.getNumber() != 0) {
                    logger.error(e.getMessage(), e);
                    pauseGame();
                    try {
                        messenger.sendMessage(chat.getId(), "Произошла ошибка, подробности см. в логе\nИгра поставлена на паузу");
                    } catch (IOException e1) {
                        logger.error(e1.getMessage(), e1);
                    }
                }
            }

        }

    }

    private void updateLevel() throws IOException {

        Level _level = dozorEngine.getLevel();

        if (level.getNumber() != _level.getNumber()) {
            messenger.sendMessage(chat.getId(), "Выдан новый уровень!");
            messenger.sendMessage(chat.getId(), _level.toString());
        }

        level = _level;

    }

    @Override
    public String toString() {
        return "Game{" + "pin='" + pin + '\'' + ", chat=" + chat.toString() + '}';
    }

}





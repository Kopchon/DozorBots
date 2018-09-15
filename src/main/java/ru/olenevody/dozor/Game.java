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
//    private boolean spoilerEnable = true;
//    private boolean endGame = false;
//    private HashMap<String, Level> levels = new HashMap<>();
//    private HashMap<String, Level> bonus_levels = new HashMap<>();
//    private boolean stopGame;
//
//
////    private boolean testGame;
//

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

                Long startTime = System.currentTimeMillis();

                updateLevel();

                Long duration = System.currentTimeMillis() - startTime;
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

//
//    public void parseMessage(JSONObject msg) {
//
//        if (gameStatus != GameStatus.Running) {
//            return;
//        }
//
//        String logString;
//
//        if (msg.containsKey("photo")) {
//            // Пересылать в чат фоток
//            logString = "#получена фотография";
//            logger.log(logString);
//            return;
//        } else if (!msg.containsKey("text")) {
//            return;
//        }
//
//        String text = msg.get("text").toString();
//        if (isCode(text)) {
//            if (!dozorEngine.isRunning()) {
//                Messenger.sendMessageReply(Constants.TOKEN, chat_id, Constants.ERROR_ENGINE_NOT_RUNNING, msg.get("message_id").toString());
//            }
//            // TODO: 29.04.2017 надо дописать обновление уровня, а то вдруг новый
//            CodeStatus codeStatus = null;
//            if (testGame) {
//                codeStatus = CodeStatus.values()[(int)(2*Math.random())];
//            } else {
//                codeStatus = dozorEngine.enterCode(text);
//            }
//
//            Level l = levels.get(level.getNumber());
//            if (l != null) {
//                JSONObject from = (JSONObject) msg.get("from");
//                long id = Long.parseLong(from.get("id").toString());
//                String first_name = from.get("first_name").toString();
//                String last_name = from.containsKey("last_name")?from.get("last_name").toString():"";
//                String username = from.containsKey("username")?from.get("username").toString():"";
//
//                l.setCodeInputs(new CodeInput(new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()), codeStatus, text, new User(id,first_name,last_name,username)));
//            }
//
//            Messenger.sendMessageReply(Constants.TOKEN, chat_id, codeStatus.toString(), msg.get("message_id").toString());
//            if (codeStatus == CodeStatus.DONE || codeStatus == CodeStatus.DONE_BONUS) {
//                // TODO: 02.07.2017 тут может быть обновление уровня и не нужно писать коды
//                if (!updateGame()) {
//                    Messenger.sendMessage(Constants.TOKEN, chat_id, getCodes(true));
//                }
//            } else {
//
//            }
//
//            JSONObject user = (JSONObject) msg.get("from");
//            logString = "#введен код#" + user.get("id").toString() + "#" + text + "#" + codeStatus.ordinal();
//            logger.log(logString);
//        } else if (isCoords(text)) {
//            // Введены координаты
//            String[] tmp = text.split("(\\s|\\.|,)");
//            if (tmp.length == 4) {
//                Messenger.sendLocation(Constants.TOKEN, chat_id, tmp[0].trim() + "." + tmp[1].trim(), tmp[2].trim() + "." + tmp[3].trim());
//            } else if (tmp.length == 5) {
//                Messenger.sendLocation(Constants.TOKEN, chat_id, tmp[0].trim() + "." + tmp[1].trim(), tmp[3].trim() + "." + tmp[4].trim());
//            }
//            //Messenger.sendMessageReply(Constants.TOKEN, chat_id, Constants.YMAP_PRIFIX + text, msg.get("message_id").toString());
////        } else if (isStreetQuery(text)) {
////            // Введен запрос поиска улицы
////            Messenger.sendMessageReply(Constants.TOKEN, chat_id, StreetParser.findStreet(text.substring(4)), msg.get("message_id").toString());
//        }
//
//    }
//
//    public String getInfo() {
//        String info = "Пин игры = " + pin + "\n";
//        info += "Пока больше информации нет =(";
//        return info;
//    }
//
//    public Level getLevel() {
//        return level;
//    }
//
//    public String getCodes(boolean onlyNotDone) {
//        return level.getCodes(onlyNotDone);
//    }
//
//    synchronized public boolean updateGame() {
//
//        boolean isNewLevel = false;
//        try {
//            ArrayList<Level> levelData = dozorEngine.getLevelData();
//            level = levelData.get(0);
//
//            if (!level.isBonus()) {
//                if (!level.getNumber().equals("\"error\"")) {
//                    if (levels.containsKey(level.getNumber())) {
//                        if (level.getSpoilerSolved() != levels.get(level.getNumber()).getSpoilerSolved() && level.getSpoilerSolved() == 1) {
//                            Messenger.sendMessage(Constants.TOKEN, chat_id, Constants.SMILE_CODE_DONE+Constants.SMILE_CODE_DONE+"Спойлер разгадан"+Constants.SMILE_CODE_DONE+Constants.SMILE_CODE_DONE);
//                            levels.get(level.getNumber()).setSpoilerSolved(1);
//                            levels.get(level.getNumber()).setSpoilerSolvedTime(new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()));
//                        }
//                        if (level.isHint1() != levels.get(level.getNumber()).isHint1() && level.isHint1()) {
//                            Messenger.sendMessage(Constants.TOKEN, chat_id, "Выдана подсказка1");
//                            levels.get(level.getNumber()).setHint1(true);
//                        }
//                        if (level.isHint2() != levels.get(level.getNumber()).isHint2() && level.isHint2()) {
//                            Messenger.sendMessage(Constants.TOKEN, chat_id, "Выдана подсказка2");
//                            levels.get(level.getNumber()).setHint2(true);
//                        }
//
//                        levels.get(level.getNumber()).setCodes(level.getCodes());
//                    } else {
//                        level.setDateToAdd(System.currentTimeMillis());
//                        levels.put(level.getNumber(), level);
//                        Messenger.sendMessage(Constants.TOKEN, chat_id, Constants.SMILE_CODE_EXCLAMATION+Constants.SMILE_CODE_EXCLAMATION+" Выдан новый #уровень " + level.getNumber()+" "+Constants.SMILE_CODE_EXCLAMATION+Constants.SMILE_CODE_EXCLAMATION);
//
//                        Messenger.sendMessage(Constants.TOKEN, chat_id, getCodes(true));
//                        isNewLevel = true;
//                    }
//                } else if (level.isFinalLevel() && !endGame) {
//                    endGame = true;
//                    Messenger.sendMessage(Constants.TOKEN, chat_id, "Вы прошли все уровни. Спасибо за игру. Игра закончена");
//                    level.setDateToAdd(System.currentTimeMillis());
//                    levels.put(level.getNumber(), level);
//                    isNewLevel = true;
//                }
//            }
//
//            if (level.isStopGame() && endGame) {
//                if (!stopGame) {
//                    getGameStat();
//                    stopGame = true;
//                }
//            } else {
//
//                //проверка что был, а теперь нет - разгадан
//                for (Map.Entry<String, Level> entry : bonus_levels.entrySet()) {
//                    if (entry.getValue().isBonusSolved())
//                        continue;
//
//                    boolean no = true;
//                    for (Level bonusLevel : levelData) {
//                        if (bonusLevel.getStartTime().equals(entry.getValue().getStartTime())) {
//                            no = false;
//                            break;
//                        }
//                    }
//                    if (no) {
//                        Messenger.sendMessage(Constants.TOKEN, chat_id, Constants.SMILE_CODE_DONE + Constants.SMILE_CODE_DONE + "Бонусный уровень, выданный в " + entry.getValue().getStartTime() + ", разгадан" + Constants.SMILE_CODE_DONE + Constants.SMILE_CODE_DONE);
//                        entry.getValue().setBonusSolved();
//                    }
//                }
//
//                for (Level bonusLevel : levelData) {
//                    if (bonusLevel.isBonus()) {
//                        if (!bonus_levels.containsKey(bonusLevel.getStartTime())) {
//                            Messenger.sendMessage(Constants.TOKEN, chat_id, Constants.SMILE_CODE_EXCLAMATION + Constants.SMILE_CODE_EXCLAMATION + "Выдан бонусный уровень. время " + bonusLevel.getStartTime() + Constants.SMILE_CODE_EXCLAMATION + Constants.SMILE_CODE_EXCLAMATION);
//                            bonusLevel.setDateToAdd(System.currentTimeMillis());
//                            bonus_levels.put(bonusLevel.getStartTime(), bonusLevel);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return isNewLevel;
//    }
//
//    synchronized public void setTestPage(String page) {
//        dozorEngine.setUrl(page);
//    }
//
//    synchronized public void setSpoiler() {
//        spoilerEnable = !spoilerEnable;
//    }
//
//    synchronized public boolean getSpoiler() {
//        return spoilerEnable;
//    }
//
//    synchronized public void getGameStat() {
//        StringBuilder gameStat;
//
//        ArrayList<Level> levelArrayList = new ArrayList<>(levels.values());
//        levelArrayList.sort((o1, o2) -> ((int)(o1.getDateToAdd() - o2.getDateToAdd())));
//
//        //for(Map.Entry<String, Level> entry:levels.entrySet()) {
//        for(Level l:levelArrayList) {
//            //Level l = entry.getValue();
//            gameStat = new StringBuilder();
//            if (l.isFinalLevel()) {
//                gameStat.append("Игра окончена в " + l.getStartTime()+"\n");
//            } else {
//                gameStat.append("уровень "+l.getNumber()+" выдан в "+l.getStartTime()+"\n");
//                if (l.getSpoilerSolved() == 1 && l.getSpoilerSolvedTime() != null) {
//                    gameStat.append("   спойлер разгадан в "+l.getSpoilerSolvedTime()+"\n");
//                }
//                for (CodeInput codeInput:l.getCodeInputs()) {
//                    gameStat.append("       в "+codeInput.getTime()+
//                                      " игрок "+codeInput.getUser()+
//                                   " ввел код "+codeInput.getCode()+"."+
//                                                codeInput.getCodeStatus()+
//                                               (codeInput.getCodeStatus()==CodeStatus.DONE || codeInput.getCodeStatus()==CodeStatus.DONE_BONUS?Constants.SMILE_CODE_DONE:Constants.SMILE_CODE_NOT_DONE)+
//                                   "\n");
//                }
//            }
//            Messenger.sendMessage(Constants.TOKEN, chat_id, gameStat.toString());
//        }
//
//        ArrayList<Level> bones_levelArrayList = new ArrayList<>(bonus_levels.values());
//        bones_levelArrayList.sort((o1, o2) -> ((int)(o1.getDateToAdd() - o2.getDateToAdd())));
//
//        //for(Map.Entry<String, Level> entry:bonus_levels.entrySet()) {
//        for(Level l:bones_levelArrayList) {
//            //Level l = entry.getValue();
//
//            Messenger.sendMessage(Constants.TOKEN, chat_id, "бонусный уровень выдан в "+l.getStartTime()+
//                                                                 " "+
//                                                                 (l.isBonusSolved()?"разгадан"+Constants.SMILE_CODE_DONE:" не разгадан"+Constants.SMILE_CODE_NOT_DONE));
//        }
//    }
//
//    synchronized public void startTestGame() {
//        testGame = true;
//
//    }


    @Override
    public String toString() {
        return "Game{" + "pin='" + pin + '\'' + ", chat=" + chat.toString() + '}';
    }
}





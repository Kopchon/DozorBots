package ru.olenevody.dozor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {

    private int number;
    private String startTime;
    private String description;

    // <Сектор, список кодов>
    private Codes codes;
    private int spoilerSolved;
    private boolean hint1;
    private boolean hint2;

    private boolean isFinalLevel;

    private ArrayList<CodeInput> codeInputs;
    private String spoilerSolvedTime;
    private boolean isBonus;
    private boolean bonusSolved;
    private long dateToAdd;
    private boolean stopGame;


    public Level() {
        codes = new Codes();
        spoilerSolved = -1;
        hint1 = false;
        hint2 = false;
        isFinalLevel = false;

        codeInputs = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public void addCodesSector(String name, List<Code> codes) {
//        this.codes.put(name, codes);
//    }

//    public String getCodes(boolean onlyNotDone) {
//        String tmp = "";
//        int notDone = 0;
//        for (Map.Entry<String, List<Code>> entry : codes.entrySet()) {
//            int count = 0;
//            tmp += "\n" + entry.getKey() + ":";
//            for (Code code : entry.getValue()) {
//                count++;
//                if (!code.isDone()) notDone++;
//                if (onlyNotDone && code.isDone())
//                    continue;
//
//                tmp += "\n" + count + "   " + code;
//            }
//        }
//        tmp += "\nВсего осталось: " + notDone;
//        return tmp;
//    }

    public void setCodes(Codes codes) {
        this.codes = codes;
    }

    public Codes getCodes() {
        return getCodes(true);
    }

    public Codes getCodes(boolean onlyNotDone) {
        if (onlyNotDone) {
            return new Codes(codes, true);
        } else {
            return codes;
        }
    }

    public int getSpoilerSolved() {
        return spoilerSolved;
    }

    public void setSpoilerSolved(int spoilerSolved) {
        this.spoilerSolved = spoilerSolved;
    }

    public boolean isHint1() {
        return hint1;
    }

    public void setHint1(boolean hint1) {
        this.hint1 = hint1;
    }

    public boolean isHint2() {
        return hint2;
    }

    public void setHint2(boolean hint2) {
        this.hint2 = hint2;
    }

    public boolean isFinalLevel() {
        return isFinalLevel;
    }

    public void setFinalLevel(boolean finalLevel) {
        isFinalLevel = finalLevel;
    }

    public ArrayList<CodeInput> getCodeInputs() {
        return codeInputs;
    }

    public void setCodeInputs(CodeInput codeInput) {
        codeInputs.add(codeInput);
    }

    public String getSpoilerSolvedTime() {
        return spoilerSolvedTime;
    }

    public void setSpoilerSolvedTime(String spoilerSolvedTime) {
        this.spoilerSolvedTime = spoilerSolvedTime;
    }

    public boolean isBonus() {
        return isBonus;
    }

    public void setIsBonus() {
        isBonus = true;
    }

    public boolean isBonusSolved() {
        return bonusSolved;
    }

    public void setBonusSolved() {
        this.bonusSolved = true;
    }

    public long getDateToAdd() {
        return dateToAdd;
    }

    public void setDateToAdd(long dateToAdd) {
        this.dateToAdd = dateToAdd;
    }

    public boolean isStopGame() {
        return stopGame;
    }

    public void setStopGame(boolean stopGame) {
        this.stopGame = stopGame;
    }

    @Override
    public String toString() {
        return  "Уровень " + number + "\n"
                + "Получено в " + startTime + "\n"
                + (description != null ? "Примечание: " + description + "\n" : "")
                + "\n" + getCodes().toString();
    }
}

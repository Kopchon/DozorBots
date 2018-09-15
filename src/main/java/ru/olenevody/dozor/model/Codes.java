package ru.olenevody.dozor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Codes {

    // Сектор, Список кодов
    private Map<String, List<Code>> codes = new TreeMap<>();

    private int total;
    private int required;
    private int done;

    public Codes() {
    }

    public Codes(Codes _codes, boolean onlyNotDone) {

        Map<String, List<Code>> codesMap = _codes.getCodes();

        for (String sector : codesMap.keySet()) {
            List<Code> sectorCodes = new ArrayList<>();
            codes.put(sector, sectorCodes);
            for (Code code : codesMap.get(sector)) {
                if (!code.isDone()) {
                    sectorCodes.add(code);
                }
            }
        }

        total = _codes.getTotal();
        required = _codes.getRequired();
        done = _codes.getDone();

    }

    public Codes(Map<String, List<Code>> codes) {
        this.codes = codes;
        calculateTotals();
    }

    public Codes(Map<String, List<Code>> codes, int total, int required, int done) {
        this.codes = codes;
        this.total = total;
        this.required = required;
        this.done = done;
    }

    public Map<String, List<Code>> getCodes() {
        return codes;
    }

    public void setCodes(Map<String, List<Code>> codes) {
        this.codes = codes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    private void calculateTotals() {

        total = 0;
        done = 0;

        for (String sector : codes.keySet()) {
            for (Code code : codes.get(sector)) {
                total++;
                if (code.isDone()) done++;
            }
        }

        required = total;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String sector : codes.keySet()) {
            sb.append(sector);
            sb.append("\n");
            for (Code code : codes.get(sector)) {
                sb.append(code.toString() + "\n");
            }
        }

        sb.append("\n");
        sb.append("Всего: " + total + "\n");
        sb.append("Достаточно: " + required + "\n");
        sb.append("Принято: " + done + "\n");
        sb.append("Осталось: " + (required - done) + "\n");

        return sb.toString();
    }
}

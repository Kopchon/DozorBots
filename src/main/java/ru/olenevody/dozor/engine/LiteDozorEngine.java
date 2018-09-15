package ru.olenevody.dozor.engine;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.olenevody.dozor.model.Code;
import ru.olenevody.dozor.model.CodeStatus;
import ru.olenevody.dozor.model.Codes;
import ru.olenevody.dozor.model.Level;
import ru.olenevody.listener.DozorListener;

import java.io.IOException;
import java.util.*;

@Component
public class LiteDozorEngine implements DozorEngine {

    @Autowired
    Properties properties;

    @Autowired
    @Qualifier("engineDozorListener")
    DozorListener dozorListener;

    String pin;

    private Logger logger = Logger.getLogger(LiteDozorEngine.class);

    public LiteDozorEngine() {
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public Level getLevel() throws IOException {

        Element body = dozorListener.getPage(pin);
        String html = body.html();

        Level level = new Level();

        level.setNumber(parseNumber(html));
        level.setStartTime(parseStartTime(html));
        level.setDescription(parseDescription(html));

        // TODO: ДОДЕЛАТЬ!

        level.setCodes(parseCodes(body));

        return level;
    }

    private int parseNumber(String html) {
        int begin = html.indexOf("<!--levelNumberBegin-->") + "<!--levelNumberBegin-->".length();
        int end = html.indexOf("<!--levelNumberEnd-->");
        String num = html.substring(begin, end).trim();
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            logger.error(num, e);
            return -1;
        }
    }

    private String parseStartTime(String html) {
        return "*тут будет время*";
    }

    private String parseDescription(String html) {

        int begin = html.indexOf("<!--taskNotes-->") + "<!--taskNotes-->".length();
        int end = html.indexOf("<!--taskNotesEnd-->");
        return html.substring(begin, end)
                .replaceAll("(<p>|</p>|<br>)", "\n")
                .replaceAll("<strong>", "<b>")
                .replaceAll("</strong>", "</b>")
                .replaceAll("&nbsp;", "")
                .trim();

    }

    private Codes parseCodes(Element body) {

        Element first = body.select(".dCodes").first();
        if (first == null) {
            return new Codes();
        }

        try {
            Map<String, List<Code>> allCodes = new TreeMap<>();
            List<Code> sectorCodes = null;

            String[] lines = first.html().replaceAll("\n", "").split("<br>");
            int codeNumber = 1;
            for (int i = 1; i < lines.length - 2; i++) {
                String line = lines[i];
                String upperLine = line.toUpperCase();
                if (upperLine.startsWith("ВНЕ СЕКТОРОВ") || upperLine.startsWith("СЕКТОР") || upperLine.startsWith("ОСНОВНЫЕ")) {
                    codeNumber = 1;
                    sectorCodes = new ArrayList<>();
                    allCodes.put(line, sectorCodes);
                } else {
                    Code code = parseCode(codeNumber++, line);
                    sectorCodes.add(code);
                }
            }

            Codes codes = new Codes(allCodes);
            String lineTotals = lines[lines.length - 2];
            if (lineTotals.contains("для прохождения достаточно любых")) {
                try {
                    String required = lineTotals.substring(lineTotals.indexOf("любых") + 6, lineTotals.lastIndexOf(",")).trim();
                    codes.setRequired(Integer.parseInt(required));
                } catch (Exception e) {
                    codes.setRequired(codes.getTotal());
                }
            }
            return codes;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Codes();
        }

    }

    private Code parseCode(int number, String code) {

        code = code.trim();
        if (code.startsWith("<span")) {
            code = Jsoup.parse(code).text();
        }
        String[] lines = code.split(" ");
        return new Code(number, !lines[0].equals("---"), lines[1].replaceAll("[\\(,\\)]", ""), lines[0]);

    }

    @Override
    public CodeStatus enterCode(String text) throws IOException {

        String page = dozorListener.enterCode(pin, text).html();

        if (page.contains("Код принят."))
            return CodeStatus.DONE;
        else if (page.contains("Принят бонусный код."))
            return CodeStatus.DONE_BONUS;
        else if (page.contains("Вы уже ввели этот"))
            return CodeStatus.DONE_AGAIN;
        else if (page.contains("Вы пытаетесь ввести уже принятый бонусный код"))
            return CodeStatus.DONE_AGAIN_BONUS;
        else if (page.contains("Вы прошли все уровни"))
            return CodeStatus.GAME_OVER;
        else
            return CodeStatus.NOT_DONE;

    }


}

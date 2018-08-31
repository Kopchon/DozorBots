package ru.olenevody.listener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.olenevody.dozor.model.Code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class FileDozorListener implements DozorListener {

    @Autowired
    Properties properties;

    @Override
    public Element getPage(String pin) throws IOException {
        return Jsoup.parse(new String(Files.readAllBytes(Paths.get(properties.get("dozor.debug.page").toString())), "Windows-1251")).body();
    }

    @Override
    public Element enterCode(String pin, String code) throws IOException {
        return Jsoup.parse("Код принят");
    }
}

package ru.olenevody.listener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.olenevody.dozor.model.CodeStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class EngineDozorListener implements DozorListener {

    @Autowired
    Properties properties;

    @Override
    public Element getPage(String pin) throws IOException {
        return Jsoup.parse(properties.get("dozor.url").toString() + pin).body();
    }

    @Override
    public Element enterCode(String pin, String code) throws IOException {

        return Jsoup.connect(properties.get("dozor.url").toString() + pin)
                    .userAgent("Mozilla")
                    .data("action", "entcod")
                    .data("pin", pin)
                    .data("cod", code)
                    .post()
                    .body();

    }
}

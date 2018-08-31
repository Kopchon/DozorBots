package ru.olenevody.listener;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface DozorListener {

    Element getPage(String pin) throws IOException;

    Element enterCode(String pin, String code) throws IOException;

}

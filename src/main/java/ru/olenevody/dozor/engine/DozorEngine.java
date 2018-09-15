package ru.olenevody.dozor.engine;

import org.springframework.stereotype.Component;
import ru.olenevody.dozor.model.CodeStatus;
import ru.olenevody.dozor.model.Level;

import java.io.IOException;

@Component
public interface DozorEngine {

    Level getLevel() throws IOException;
    CodeStatus enterCode(String text) throws IOException;

    void setPin(String pin);
    String getPin();

}

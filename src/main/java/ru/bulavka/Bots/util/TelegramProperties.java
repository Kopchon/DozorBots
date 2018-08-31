package ru.bulavka.Bots.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TelegramProperties {

    public static Properties properties = new Properties();

    public TelegramProperties() {

        try {
            properties.load(new FileInputStream("telegram.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package ru.bulavka.Bots.listener;

import org.springframework.beans.factory.annotation.Value;
import ru.bulavka.Bots.model.Updates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

@JsonIgnoreProperties({"mapper, url"})
public class SimpleTelegramListener implements Listener {

    private String token;
    private long offset;
    private ObjectMapper mapper;
    private UpdatesFilter updatesFilter;

    @Value("${telegram.url}")
    private String url;

    public SimpleTelegramListener(String token) {
        this(token, null, null);
    }

    public SimpleTelegramListener(String token, ObjectMapper mapper) {
        this(token, mapper, null);
    }

    public SimpleTelegramListener(String token, ObjectMapper mapper, UpdatesFilter updatesFilter) {
        this.token = token;
        this.mapper = mapper;
        this.updatesFilter = updatesFilter;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @JsonIgnore
    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @JsonIgnore
    @Override
    public Updates getUpdates() throws IOException {

        String json = Jsoup
                .connect(String.format(url + "%s/getUpdates", token))
                .method(Connection.Method.GET)
                .ignoreContentType(true)
                .data("offset", String.format("%d", offset))
                .execute()
                .body();

        Updates updates = mapper.readValue(json, Updates.class);
        if (updates.size() > 0) {
            offset = updates.getOffset() + 1;
        }
        if (updatesFilter != null) {
            updates = updatesFilter.filter(updates);
        }
        return updates;

    }

}

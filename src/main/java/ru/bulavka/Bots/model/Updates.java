package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Updates {

    @JsonProperty(required = true)
    private boolean ok;

    private List<Update> result = new ArrayList<>();
    private String description;
    private int error_code;

    public boolean isOk() {
        return ok;
    }

    public List<Update> getResult() {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public int getError_code() {
        return error_code;
    }

    public int size() {
        return result.size();
    }

    public long getOffset() {
        if (size() > 0) {
            return result.get(size() - 1).getUpdate_id();
        } else {
            return 0;
        }
    }

}

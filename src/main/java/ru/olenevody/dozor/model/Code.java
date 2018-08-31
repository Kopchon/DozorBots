package ru.olenevody.dozor.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Code {

    @Autowired
    Properties properties;

    private boolean done;
    private KO ko;
    private String code;

    public Code() {
    }

    public Code(boolean done, String strKO) {
        this(done, KO.getKO(strKO));
    }

    public Code(boolean done, KO ko) {
        this(done, ko, "---");
    }

    public Code(boolean done, KO ko, String code) {
        this.done = done;
        this.ko = ko;
        this.code = code;
    }

    public Code(boolean done, String ko, String code) {
        this.done = done;
        this.ko = KO.getKO(ko);
        this.code = code;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public KO getKo() {
        return ko;
    }

    public void setKo(KO ko) {
        this.ko = ko;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + "\t(" + ko + ")";
    }

}
package ru.olenevody.dozor.model;

public enum KO {

    KO_1("1"), KO_1plus("1+"), KO_2("2"), KO_2plus("2+"), KO_3("3"), KO_3plus("3+"), KO_NULL("null");

    private String ko_string;

    KO(String ko_string) {
        this.ko_string = ko_string;
    }

    public static KO getKO(String strKO) {

        switch (strKO.trim().toUpperCase()) {
            case "1":
                return KO_1;
            case "1+":
                return KO_1plus;
            case "2":
                return KO_2;
            case "2+":
                return KO_2plus;
            case "3":
                return KO_3;
            case "3+":
                return KO_3plus;
            case "NULL":
                return KO_NULL;
            default:
                return KO_NULL;
        }
    }

    @Override
    public String toString() {

        return ko_string;

    }
}
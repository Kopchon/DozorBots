package ru.bulavka.Bots.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    @JsonProperty(required = true)
    private double longitude;

    @JsonProperty(required = true)
    private double latitude;

    public Location() {
    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

}

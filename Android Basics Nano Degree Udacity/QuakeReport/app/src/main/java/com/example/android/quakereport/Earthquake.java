package com.example.android.quakereport;

public class Earthquake {
    private double magnitude;
    private String cityName;
    private long timeMilliSeconds;
    private String url;

    public Earthquake(double magnitude, String cityName,long timeMilliSeconds, String url) {
        this.magnitude = magnitude;
        this.cityName = cityName;
        this.timeMilliSeconds = timeMilliSeconds;
        this.url = url;
    }
    public double getMagnitude() {
        return magnitude;
    }

    public String getCityName() {
        return cityName;
    }

    public long getTime() {
        return timeMilliSeconds;
    }

    public String getUrl() {
        return url;
    }
}

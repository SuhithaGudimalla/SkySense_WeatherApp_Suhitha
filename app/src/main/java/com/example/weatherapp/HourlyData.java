package com.example.weatherapp;

public class HourlyData {
    private String hour;
    private String temperature;

    public HourlyData(String hour, String temperature) {
        this.hour = hour;
        this.temperature = temperature;
    }

    public String getHour() {
        return hour;
    }

    public String getTemperature() {
        return temperature;
    }
}

package com.example.weatherapp;

public class HourlyData {
    private String time;
    private String temperature;
    private String condition; // New field for weather condition

    public HourlyData(String time, String temperature, String condition) {
        this.time = time;
        this.temperature = temperature;
        this.condition = condition; // Initialize condition
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCondition() { // Getter for condition
        return condition;
    }
}

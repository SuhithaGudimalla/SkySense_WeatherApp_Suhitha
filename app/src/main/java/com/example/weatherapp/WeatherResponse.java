package com.example.weatherapp;

public class WeatherResponse {
    public Main main;
    public Wind wind;
    public Sys sys;
    public Weather[] weather;

    public class Main {
        public double temp;
        public int humidity;
        public int sea_level;
    }

    public class Wind {
        public double speed;
    }

    public class Sys {
        public long sunrise;
        public long sunset;
    }

    public class Weather {
        public String main;
    }
}

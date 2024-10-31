package com.example.weatherapp;

import java.util.List;

public class HourlyResponse {
    private Forecast forecast;

    public Forecast getForecast() {
        return forecast;
    }

    public class Forecast {
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return forecastday;
        }
    }

    public class ForecastDay {
        private List<Hour> hour;

        public List<Hour> getHour() {
            return hour;
        }
    }

    public class Hour {
        private String time;
        private double temp_c; // Or change this based on the API response

        public String getTime() {
            return time;
        }

        public double getTempC() {
            return temp_c;
        }
    }
}

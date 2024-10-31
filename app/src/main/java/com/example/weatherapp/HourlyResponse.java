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
        private double temp_c; // Temperature in Celsius
        private Condition condition; // Add this line for the condition

        public String getTime() {
            return time;
        }

        public double getTempC() {
            return temp_c;
        }

        public Condition getCondition() { // Add this method to get the condition
            return condition;
        }
    }

    // Define the Condition class to hold condition-related data
    public class Condition {
        private String text; // The textual representation of the weather condition
        private String icon; // Optional: URL for the condition icon

        public String getText() {
            return text;
        }

        public String getIcon() {
            return icon; // Optional: method to get icon URL
        }
    }
}

package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather") // Specify the endpoint for the weather data
    Call<WeatherResponse> getWeatherData(
            @Query("q") String city, // Query parameter for the city name
            @Query("appid") String appid, // Query parameter for the API key
            @Query("units") String units // Query parameter for units (metric or imperial)
    );
}

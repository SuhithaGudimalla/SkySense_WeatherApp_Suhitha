package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceHourly {
    @GET("forecast.json")
    Call<HourlyResponse> getHourlyWeather(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("hours") int hours
    );
}

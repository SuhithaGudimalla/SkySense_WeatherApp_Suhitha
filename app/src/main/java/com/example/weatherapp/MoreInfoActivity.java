package com.example.weatherapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoreInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewHourly;
    private HourlyAdapter hourlyAdapter;
    private static final String API_KEY = "ef7ef39d55d84036b38103107243110"; // Your API key
    private static final String LOCATION = "Hyderabad"; // Your location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_info);

        recyclerViewHourly = findViewById(R.id.recyclerViewHourly);
        recyclerViewHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fetchHourlyWeather();
    }

    private void fetchHourlyWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterfaceHourly apiInterfaceHourly = retrofit.create(ApiInterfaceHourly.class);
        Call<HourlyResponse> call = apiInterfaceHourly.getHourlyWeather(API_KEY, LOCATION, 1, 24);

        call.enqueue(new Callback<HourlyResponse>() {
            @Override
            public void onResponse(Call<HourlyResponse> call, Response<HourlyResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", response.body().toString()); // Log the response for debugging
                    List<HourlyData> hourlyDataList = new ArrayList<>();
                    for (HourlyResponse.ForecastDay forecastDay : response.body().getForecast().getForecastday()) {
                        if (forecastDay.getHour() != null) { // Check if hour list is not null
                            for (HourlyResponse.Hour hour : forecastDay.getHour()) {
                                String time = hour.getTime();
                                String temperature = String.valueOf(hour.getTempC()); // Corrected line
                                hourlyDataList.add(new HourlyData(time, temperature));
                            }
                        }
                    }
                    hourlyAdapter = new HourlyAdapter(hourlyDataList);
                    recyclerViewHourly.setAdapter(hourlyAdapter);
                } else {
                    Log.e("API Error", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<HourlyResponse> call, Throwable t) {
                Log.e("API Error", "Failure: " + t.getMessage());
            }
        });
    }
}

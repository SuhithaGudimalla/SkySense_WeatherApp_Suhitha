package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.airbnb.lottie.LottieAnimationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDate, textViewTemp, textViewHumidity, textViewWindSpeed, textViewCondition, textViewSunrise, textViewSunset, textViewSeaLevel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        SearchView searchView = findViewById(R.id.searchView);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTemp = findViewById(R.id.textViewTemperature);
        textViewHumidity = findViewById(R.id.linearLayoutHumidity).findViewById(R.id.textViewHumidityValue);
        textViewWindSpeed = findViewById(R.id.linearLayoutWindSpeed).findViewById(R.id.textViewWindSpeedValue);
        textViewCondition = findViewById(R.id.linearLayoutCondition).findViewById(R.id.textViewConditionValue);
        textViewSunrise = findViewById(R.id.linearLayoutSunrise).findViewById(R.id.textViewSunriseValue);
        textViewSunset = findViewById(R.id.linearLayoutSunset).findViewById(R.id.textViewSunsetValue);
        textViewSeaLevel = findViewById(R.id.linearLayoutSea).findViewById(R.id.textViewSeaValue);

        // Initialize Lottie animation view if needed
        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);

        // Set up search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeatherData(query);
                return true; // Return true to indicate that the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Button to navigate to MoreInfoActivity
        Button buttonMoreInfo = findViewById(R.id.button);
        buttonMoreInfo.setOnClickListener(v -> {
            String cityName = searchView.getQuery().toString();
            if (!cityName.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, MoreInfoActivity.class);
                intent.putExtra("CITY_NAME", cityName); // Pass the city name
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a city name.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchWeatherData(String city) {
        String apiKey = "a89918b99ae5dfa22c4e64125fdfa992"; // Secure this in production

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<WeatherResponse> call = apiInterface.getWeatherData(city, apiKey, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();

                    // Check if the necessary data is available
                    if (weatherResponse != null && weatherResponse.main != null && weatherResponse.sys != null) {
                        double temperature = weatherResponse.main.temp;
                        int humidity = weatherResponse.main.humidity;
                        double windSpeed = weatherResponse.wind.speed;
                        String condition = weatherResponse.weather.length > 0 ? weatherResponse.weather[0].main : "Unknown";

                        long sunriseTimestamp = weatherResponse.sys.sunrise;
                        long sunsetTimestamp = weatherResponse.sys.sunset;

                        // Update the UI with fetched data
                        textViewTemp.setText(String.format("%s°C", temperature));
                        textViewHumidity.setText(String.format("%d %%", humidity));
                        textViewWindSpeed.setText(String.format("%.2f m/s", windSpeed));
                        textViewCondition.setText(condition);
                        textViewSunrise.setText(convertTimestampToTime(sunriseTimestamp));
                        textViewSunset.setText(convertTimestampToTime(sunsetTimestamp));

                        // Sea Level
                        textViewSeaLevel.setText(weatherResponse.main.sea_level != 0 ? String.format("%d hPa", weatherResponse.main.sea_level) : "N/A");

                        // Set the date
                        textViewDate.setText(getCurrentDate());
                    } else {
                        Toast.makeText(MainActivity.this, "Weather data is incomplete!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "City not found or error fetching data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch weather data! Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertTimestampToTime(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(timestamp * 1000));
    }

    private String getCurrentDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}

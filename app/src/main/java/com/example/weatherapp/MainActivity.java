package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast; // Import Toast for user feedback

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

        SearchView searchView = findViewById(R.id.searchView);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTemp = findViewById(R.id.textViewTemperature);
        textViewHumidity = findViewById(R.id.linearLayoutHumidity).findViewById(R.id.textViewHumidityValue);
        textViewWindSpeed = findViewById(R.id.linearLayoutWindSpeed).findViewById(R.id.textViewWindSpeedValue);
        textViewCondition = findViewById(R.id.linearLayoutCondition).findViewById(R.id.textViewConditionValue);
        textViewSunrise = findViewById(R.id.linearLayoutSunrise).findViewById(R.id.textViewSunriseValue);
        textViewSunset = findViewById(R.id.linearLayoutSunset).findViewById(R.id.textViewSunsetValue);
        textViewSeaLevel = findViewById(R.id.linearLayoutSea).findViewById(R.id.textViewSeaValue);

        LottieAnimationView animationView = findViewById(R.id.lottieAnimationView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeatherData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
                    double temperature = weatherResponse.main.temp;
                    int humidity = weatherResponse.main.humidity;
                    double windSpeed = weatherResponse.wind.speed;
                    String condition = weatherResponse.weather[0].main;

                    long sunriseTimestamp = weatherResponse.sys.sunrise;
                    long sunsetTimestamp = weatherResponse.sys.sunset;

                    String sunrise = convertTimestampToTime(sunriseTimestamp);
                    String sunset = convertTimestampToTime(sunsetTimestamp);

                    // Update the UI
                    textViewTemp.setText(String.format("%s°C", temperature));
                    textViewHumidity.setText(String.format("%d %%", humidity));
                    textViewWindSpeed.setText(String.format("%.2f m/s", windSpeed));
                    textViewCondition.setText(condition);
                    textViewSunrise.setText(sunrise);
                    textViewSunset.setText(sunset);

                    // Sea Level
                    if (weatherResponse.main.sea_level != 0) {
                        textViewSeaLevel.setText(String.format("%d hPa", weatherResponse.main.sea_level));
                    } else {
                        textViewSeaLevel.setText("N/A");
                    }

                    // Set the date
                    textViewDate.setText(getCurrentDate());
                } else {
                    Toast.makeText(MainActivity.this, "City not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch weather data!", Toast.LENGTH_SHORT).show();
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

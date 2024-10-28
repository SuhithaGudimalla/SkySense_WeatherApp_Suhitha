package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private TextView textViewToday;
    private TextView textViewTemperature;
    private ImageView imageViewLocation;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the layout for this activity

        // Initialize views
        searchView = findViewById(R.id.searchView);
        textViewToday = findViewById(R.id.textView8);
        textViewTemperature = findViewById(R.id.textView9);
        imageViewLocation = findViewById(R.id.imageView2);

        lottieAnimationView = findViewById(R.id.lottieAnimationView); // Ensure this ID matches your layout

        // Set up the search view listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search action
                Toast.makeText(MainActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                // You can implement the weather search logic here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Handle text change in the search view
                return false;
            }
        });
    }
}

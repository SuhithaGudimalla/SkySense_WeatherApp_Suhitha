// SplashActivity.java
package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Make sure this points to your correct layout file

        // Optional: Add a delay if you want to see the splash screen for a bit
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        }, 2000); // 2 seconds delay
    }
}

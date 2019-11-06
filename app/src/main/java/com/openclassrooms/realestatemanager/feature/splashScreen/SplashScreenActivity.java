package com.openclassrooms.realestatemanager.feature.splashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SLPASH_SCREEN_TIMEOUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Handler Post Delayed
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, SLPASH_SCREEN_TIMEOUT);
    }
}

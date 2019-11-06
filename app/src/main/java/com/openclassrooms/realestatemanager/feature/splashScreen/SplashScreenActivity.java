package com.openclassrooms.realestatemanager.feature.splashScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SLPASH_SCREEN_TIMEOUT = 5000;
    private ActivitySplashScreenBinding activitySplashScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        //Handler Post Delayed
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, SLPASH_SCREEN_TIMEOUT);
    }
}

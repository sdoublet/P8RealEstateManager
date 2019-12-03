package com.openclassrooms.realestatemanager.feature.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Splash;
import com.openclassrooms.realestatemanager.databinding.ActivitySplashScreenBinding;

//import static androidx.databinding.library.baseAdapters.BR.view_model;


import static com.openclassrooms.realestatemanager.BR.view_model;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SLPASH_SCREEN_TIMEOUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashViewModel splashViewModel = new SplashViewModel();
        ActivitySplashScreenBinding activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        activitySplashScreenBinding.logo.setImageResource(splashViewModel.splash.getLogo());
        activitySplashScreenBinding.textSplash.setText(splashViewModel.splash.getEstateName());


        //Handler Post Delayed
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, SLPASH_SCREEN_TIMEOUT);
    }
}

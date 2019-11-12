package com.openclassrooms.realestatemanager.feature.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.openclassrooms.realestatemanager.BR;
import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySplashScreenBinding;

import static com.openclassrooms.realestatemanager.BR.view_model;

public class SplashScreenActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;
    public static final int SLPASH_SCREEN_TIMEOUT = 5000;
    private ActivitySplashScreenBinding activitySplashScreenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashViewModel = new SplashViewModel();
        activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
       // Splash spashModel = new Splash(R.drawable.real_estate, "Real Estate Manager");
       // activitySplashScreenBinding.setSplash(spashModel);
        activitySplashScreenBinding.setVariable(view_model, splashViewModel);


        //Handler Post Delayed
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, SLPASH_SCREEN_TIMEOUT);
    }
}

package com.openclassrooms.realestatemanager.feature.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.MainActivity;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivitySplashScreenBinding;
import com.openclassrooms.realestatemanager.feature.login.LoginActivity;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.User;

import java.util.List;



public class SplashScreenActivity extends AppCompatActivity {

    public static final int SLPASH_SCREEN_TIMEOUT = 5000;
    EstateViewModel estateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashViewModel splashViewModel = new SplashViewModel();
        this.configureViewModel();
        ActivitySplashScreenBinding activitySplashScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        activitySplashScreenBinding.logo.setImageResource(splashViewModel.splash.getLogo());
        activitySplashScreenBinding.textSplash.setText(splashViewModel.splash.getEstateName());


        //Handler Post Delayed
        new Handler().postDelayed(this::getUser, SLPASH_SCREEN_TIMEOUT);


    }

    //---------------------
    // Configure ViewModel
    //---------------------
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.estateViewModel = ViewModelProviders.of(this, viewModelFactory).get(EstateViewModel.class);
    }

    private void getUser(){
        estateViewModel.getAllUsers().observe(this, this::launchFirstConnection);
    }

    private void launchFirstConnection(List<User> userList){
        Log.e("userlist", String.valueOf(userList.size()));
        if (userList.size()>=1){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

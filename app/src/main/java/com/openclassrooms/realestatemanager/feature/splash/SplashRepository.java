package com.openclassrooms.realestatemanager.feature.splash;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Splash;

public class SplashRepository implements SplashModel {
    @Override
    public Splash getSplash() {
        Splash splashModel = new Splash(R.drawable.real_estate, "Real Estate Manager");
        return splashModel;
    }
}

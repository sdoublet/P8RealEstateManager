package com.openclassrooms.realestatemanager.feature.splash;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Splash;

public class SplashRepository implements SplashModel {
    @Override
    public Splash getSplash() {
        return new Splash(R.drawable.real_estate, "Real Estate Manager");
    }
}

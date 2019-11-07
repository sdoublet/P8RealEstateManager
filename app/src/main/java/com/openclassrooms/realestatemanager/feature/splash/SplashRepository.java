package com.openclassrooms.realestatemanager.feature.splash;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.data.Spash;

public class SplashRepository implements SplashModel {
    @Override
    public Spash getSplash() {
        Spash spashModel = new Spash(R.drawable.real_estate, "Real Estate Manager");
        return spashModel;
    }
}

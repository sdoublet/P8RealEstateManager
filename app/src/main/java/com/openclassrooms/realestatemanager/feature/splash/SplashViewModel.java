package com.openclassrooms.realestatemanager.feature.splash;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.data.Spash;

public class SplashViewModel extends ViewModel implements Observable {

    @Bindable
    Spash spash;
    SplashModel splashModel;
    @Bindable
    PropertyChangeRegistry callbacks;

    public SplashViewModel() {
        splashModel = new SplashRepository();
        spash = splashModel.getSplash();
        callbacks = new PropertyChangeRegistry();
    }


    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }


}

package com.openclassrooms.realestatemanager.feature.splash;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Splash;

public class SplashViewModel extends ViewModel implements Observable {

    @Bindable
    Splash splash;
    SplashModel splashModel;
    @Bindable
    PropertyChangeRegistry callbacks;

    public SplashViewModel() {
        splashModel = new SplashRepository();
        splash = splashModel.getSplash();
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

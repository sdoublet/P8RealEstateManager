package com.openclassrooms.realestatemanager.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.EstateViewModel;
import com.openclassrooms.realestatemanager.feature.show_property.EstateViewHolder;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final EstateDataRepository estateDataRepository;
    private final UserDataRepository userDataRepository;
    private final PictureDataRepository pictureDataRepository;
    private final Executor executor;

    public ViewModelFactory(EstateDataRepository estateDataRepository, UserDataRepository userDataRepository, PictureDataRepository pictureDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.userDataRepository = userDataRepository;
        this.pictureDataRepository = pictureDataRepository;
        this.executor = executor;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)){
            return (T) new EstateViewModel(estateDataRepository, userDataRepository, pictureDataRepository, executor);

        }else {
            throw new IllegalArgumentException("vieModel not found");
        }
    }
}

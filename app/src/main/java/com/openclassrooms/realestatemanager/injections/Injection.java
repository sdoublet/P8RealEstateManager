package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.database.RoomDb;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateDataRepository provideEstateDataSource(Context context){
        RoomDb roomDb = RoomDb.getInstance(context);
        return new EstateDataRepository(roomDb.estateDao());
    }

    public static UserDataRepository provideUserDataSource(Context context){
        RoomDb roomDb = RoomDb.getInstance(context);
        return new UserDataRepository(roomDb.userDao());
    }

    public static PictureDataRepository providePictureDataSource(Context context){
        RoomDb roomDb = RoomDb.getInstance(context);
        return new PictureDataRepository(roomDb.pictureDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context){
        EstateDataRepository estateDataRepository = provideEstateDataSource(context);
        UserDataRepository userDataRepository = provideUserDataSource(context);
        PictureDataRepository pictureDataRepository = providePictureDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(estateDataRepository, userDataRepository, pictureDataRepository, executor);
    }
}

package com.openclassrooms.realestatemanager;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public  class EstateViewModel extends ViewModel {

    //REPOSITORIES
    private final EstateDataRepository estateDataRepository;
    private final UserDataRepository userDataRepository;
    private final PictureDataRepository pictureDataRepository;
    private final Executor executor;

    //DATA
    private LiveData<User> currentUser;
    private LiveData<Estate> currentEstate;
    private LiveData<Picture> currentPicture;

    private LiveData<List<Estate>> allEstate;

    public EstateViewModel(EstateDataRepository estateDataRepository, UserDataRepository userDataRepository, PictureDataRepository pictureDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.userDataRepository = userDataRepository;
        this.pictureDataRepository = pictureDataRepository;
        this.executor = executor;
    }

    public void intit(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = userDataRepository.getUser(userId);
    }

    public void initEstate(long estateId) {
        if (this.currentEstate != null) {
            return;
        }
        currentEstate = estateDataRepository.getEstateFromId(estateId);
    }


    //------------------
    // FOR USER
    //------------------
    public LiveData<User> getUser(long userId) {
        return this.currentUser;
    }

    //------------------
    // FOR ESTATE
    //------------------
    public LiveData<List<Estate>> getAllEstates() {
        return estateDataRepository.getAllEstate();
    }

    public LiveData<Estate> getEstateFromId(long estateId) {
        return estateDataRepository.getEstateFromId(estateId);
    }

    public LiveData<List<Estate>> getEstatePerAgent(long agentId) {
        return estateDataRepository.getEstatePerAgent(agentId);
    }


    public LiveData<List<Estate>> displaySoldEstateDesc(){
        return estateDataRepository.displaySoldEstateDesc();
    }
    public LiveData<List<Estate>> displaySoldEstateAsc(){
        return estateDataRepository.displaySoldEstateAsc();
    }



    //test with raw query
    public LiveData<List<Estate>> getEstateByFilter(SupportSQLiteQuery query){return estateDataRepository.getEstateByFilter(query);}

    public LiveData<Integer> getLastEstate(){
        return estateDataRepository.getLastEstate();
    }

    public Estate getTheLast(){
        return estateDataRepository.getTheLast();
    }


    public void createEstate(Estate estate) {
        Gson gson = new Gson();
        Log.e("gson", gson.toJson(estate));
        executor.execute(() -> estateDataRepository.createEstate(estate));
    }

    public void deleteEstate(long estateId) {
        executor.execute(() -> estateDataRepository.deleteEstate(estateId));
    }

    public void updateEstate(Estate estate) {
        executor.execute(() -> estateDataRepository.updateEstate(estate));
    }

    //-----------------
    //FOR PICTURES
    //-----------------
    public LiveData<List<Picture>> getAllPicturesFromEstate(long estateId) {
        return pictureDataRepository.getAllPictureFromEstateId(estateId);
    }

    public LiveData<List<Picture>> getAllPictures(){
        return pictureDataRepository.getAllPictures();
    }

    public LiveData<Picture> getPictureByIdAsc(long estateId){
        return pictureDataRepository.getPictureByIdAsc(estateId);
    }

    public void createPicture(Picture picture) {
        executor.execute(() -> pictureDataRepository.createPicture(picture));
    }

    public void deletePicture(Picture picture) {
        executor.execute(() -> pictureDataRepository.deletePicture(picture));
    }

}
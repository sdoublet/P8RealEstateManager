package com.openclassrooms.realestatemanager.feature.show_property;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.data.User;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    //REPOSITORIES
    private final EstateDataRepository estateDataRepository;
    private final UserDataRepository userDataRepository;
    private final Executor executor;

    //DATA
    private LiveData<User> currentUser;

    public EstateViewModel(EstateDataRepository estateDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.userDataRepository = userDataRepository;
        this.executor = executor;
    }

    public void intit(int userId){
        if (this.currentUser!=null){
            return;
        }
        currentUser = userDataRepository.getUser(userId);
    }

    //------------------
    // FOR USER
    //------------------
    public LiveData<User> getUser(long userId){return this.currentUser;}

    //------------------
    // FOR ESTATE
    //------------------
    public LiveData<List<Estate>> getEstates(int userId){
        return estateDataRepository.getEstate(userId);
    }

    public void createEstate(Estate estate){
        executor.execute(()-> estateDataRepository.createEstate(estate));
    }

    public void deleteEstate(long estateId){
        executor.execute(()-> estateDataRepository.deleteEstate(estateId));
    }

    public void updateEstate(Estate estate){
        executor.execute(()-> estateDataRepository.updateEstate(estate));
    }

}

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
    private LiveData<Estate> currentEstate;

    public EstateViewModel(EstateDataRepository estateDataRepository, UserDataRepository userDataRepository, Executor executor) {
        this.estateDataRepository = estateDataRepository;
        this.userDataRepository = userDataRepository;
        this.executor = executor;
    }

    public void intit(long userId){
        if (this.currentUser!=null){
            return;
        }
        currentUser = userDataRepository.getUser(userId);
    }
    public void initEstate(long estateId){
        if (this.currentEstate!=null){
          return;
        }
        currentEstate = estateDataRepository.getEstateFromId(estateId);
    }

    //------------------
    // FOR USER
    //------------------
    public LiveData<User> getUser(long userId){return this.currentUser;}

    //------------------
    // FOR ESTATE
    //------------------
    public LiveData<List<Estate>> getAllEstates(){
        return estateDataRepository.getAllEstate();
    }

    public LiveData<Estate> getEstateFromId(long estateId){
        return estateDataRepository.getEstateFromId(estateId);
    }

    public LiveData<List<Estate>> getEstatePerAgent(long agentId){
        return estateDataRepository.getEstatePerAgent(agentId);
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

package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    //--------GET---------
    public LiveData<List<Estate>> getAllEstate(){return this.estateDao.getAllEstate();}
    public LiveData<Estate> getEstateFromId(long estateId){return this.estateDao.getEstateFromId(estateId);}
    public LiveData<List<Estate>> getEstatePerAgent(long agentId){return this.estateDao.getEstatePerAgent(agentId);}
    public LiveData<List<Estate>> displayEstateByProceDesc(){return this.estateDao.displayEstateBuyPriceDesc();}
    public LiveData<List<Estate>> displayEstateByProceAsc(){return this.estateDao.displayEstateBuyPriceAsc();}
    public LiveData<Integer> getLastEstate(){return this.estateDao.getLastEstate();}

    //------CREATE---------
    public void createEstate(Estate estate){ estateDao.insertEstate(estate);}

    //-----DELETE----------
    public void deleteEstate(long estateId){estateDao.deleteEstate(estateId);}

    //------UPDATE---------
    public void updateEstate(Estate estate){estateDao.updateEstate(estate);}
}

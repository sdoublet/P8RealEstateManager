package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    //--------GET---------
    public LiveData<List<Estate>> getEstate(long agentId){return this.estateDao.getEstate(agentId);}

    //------CREATE---------
    public void createEstate(Estate estate){ estateDao.insertEstate(estate);}

    //-----DELETE----------
    public void deleteEstate(long estateId){estateDao.deleteEstate(estateId);}

    //------UPDATE---------
    public void updateEstate(Estate estate){estateDao.updateEstate(estate);}
}

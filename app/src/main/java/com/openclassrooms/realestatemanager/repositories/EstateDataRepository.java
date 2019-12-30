package com.openclassrooms.realestatemanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.PictureDao;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class EstateDataRepository {

    private final EstateDao estateDao;
    private final PictureDao pictureDao;

    public EstateDataRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
        pictureDao = null;
    }

    public EstateDataRepository(EstateDao estateDao, PictureDao pictureDao) {
        this.estateDao = estateDao;
        this.pictureDao = pictureDao;
    }

    //--------GET---------
    public LiveData<List<Estate>> getAllEstate(){return this.estateDao.getAllEstate();}
    public LiveData<Estate> getEstateFromId(long estateId){return this.estateDao.getEstateFromId(estateId);}
    public LiveData<List<Estate>> getEstatePerAgent(long agentId){return this.estateDao.getEstatePerAgent(agentId);}

    public LiveData<List<Estate>>displaySoldEstateDesc(){return this.estateDao.displaySoldEstateDesc();}
    public LiveData<List<Estate>>displaySoldEstateAsc(){return this.estateDao.displaySoldEstateAsc();}


    //test raw query
    public LiveData<List<Estate>> getEstateByFilter(SupportSQLiteQuery query){return estateDao.getEstateByFilter(query);}
    public LiveData<Integer> getLastEstate(){return this.estateDao.getLastEstate();}
    public Estate getTheLast(){return this.estateDao.getTheLast();}

    //------CREATE---------
//    public void createEstate(Estate estate, List<Picture> pictures){
//        long estateId =  estateDao.insertEstate(estate);
//
//    }
    public void createEstate(Estate estate){
        long estateId =  estateDao.insertEstate(estate);

    }

    //-----DELETE----------
    public void deleteEstate(long estateId){estateDao.deleteEstate(estateId);}

    //------UPDATE---------
    public void updateEstate(Estate estate){estateDao.updateEstate(estate);}
}

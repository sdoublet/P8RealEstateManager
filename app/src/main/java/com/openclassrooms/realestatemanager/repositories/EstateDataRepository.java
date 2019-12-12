package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

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
    public LiveData<List<Estate>> displayEstateByProceDesc(){return this.estateDao.displayEstateBuyPriceDesc();}
    public LiveData<List<Estate>> displayEstateByProceAsc(){return this.estateDao.displayEstateBuyPriceAsc();}
    public LiveData<List<Estate>>displayEstateByNbRoomDesc(){return this.estateDao.displayestateByNbRoomDesc();}
    public LiveData<List<Estate>>displayEstateByNbRoomAsc(){return this.estateDao.displayestateByNbRoomAsc();}
    public LiveData<List<Estate>>displayEstateBySurfaceDesc(){return this.estateDao.displayestateBySurfaceDesc();}
    public LiveData<List<Estate>>displayEstateBySurfaceAsc(){return this.estateDao.displayestateBySurfaceAsc();}
    public LiveData<List<Estate>>displayEstateByTypeDesc(){return this.estateDao.displayestateByTypeDesc();}
    public LiveData<List<Estate>>displayEstateByTypeAsc(){return this.estateDao.displayestateByTypeAsc();}
    public LiveData<List<Estate>>displaySoldEstateDesc(){return this.estateDao.displaySoldEstateDesc();}
    public LiveData<List<Estate>>displaySoldEstateAsc(){return this.estateDao.displaySoldEstateAsc();}

    public LiveData<Integer> getLastEstate(){return this.estateDao.getLastEstate();}

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

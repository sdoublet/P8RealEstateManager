package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PictureDao;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class PictureDataRepository {

    private final PictureDao pictureDao;

    public PictureDataRepository(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    //--------CREATE----------
    public void createPicture(Picture picture){pictureDao.insertPicture(picture);}

    //--------DELETE-----------
    public void deletePicture(Picture picture){pictureDao.deletePicture(picture);}

    //-------GET---------------
    public LiveData<List<Picture>> getAllPictureFromEstateId(long estateId){return this.pictureDao.getPicture(estateId);}

    public LiveData<List<Picture>> getAllPictures(){return this.pictureDao.getAllPicture();}
}

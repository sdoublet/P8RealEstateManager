package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

@Dao
public interface PictureDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insertPicture(Picture picture);

    @Query("SELECT * FROM Picture WHERE estateId = :estateId")
    LiveData<List<Picture>> getPicture(long estateId);

    @Delete
    void deletePicture(Picture picture);

    @Query("SELECT * FROM Picture WHERE estateId= :estateId")
    Cursor getPictureWithCursor(long estateId);
}

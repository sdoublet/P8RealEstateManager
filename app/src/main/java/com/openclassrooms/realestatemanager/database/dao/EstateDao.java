package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate ORDER BY estateId DESC")
    LiveData<List<Estate>> getAllEstate();

    @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    LiveData<List<Estate>> getEstatePerAgent(long agentId);

    //Price
    @Query("SELECT * FROM Estate ORDER BY price DESC")
    LiveData<List<Estate>> displayEstateBuyPriceDesc();

    @Query("SELECT * FROM Estate ORDER BY price ASC")
    LiveData<List<Estate>> displayEstateBuyPriceAsc();

    //nbRoom

    @Query("SELECT * FROM Estate ORDER BY nbRoom DESC")
    LiveData<List<Estate>> displayestateByNbRoomDesc();

    @Query("SELECT * FROM Estate ORDER BY nbRoom ASC")
    LiveData<List<Estate>> displayestateByNbRoomAsc();

    //Surface
    @Query("SELECT * FROM Estate ORDER BY surface DESC")
    LiveData<List<Estate>>displayestateBySurfaceDesc();

     @Query("SELECT * FROM Estate ORDER BY surface ASC")
    LiveData<List<Estate>>displayestateBySurfaceAsc();

    //Type
    @Query("SELECT * FROM Estate ORDER BY type DESC")
    LiveData<List<Estate>>displayestateByTypeDesc();

    @Query("SELECT * FROM Estate ORDER BY type ASC")
    LiveData<List<Estate>>displayestateByTypeAsc();

    //Sold
    @Query("SELECT * FROM Estate WHERE sold ORDER BY price DESC")
    LiveData<List<Estate>>displaySoldEstateDesc();

    @Query("SELECT * FROM Estate WHERE sold ORDER BY price ASC")
    LiveData<List<Estate>>displaySoldEstateAsc();


    @Query("SELECT * FROM Estate WHERE estateId = :estateId")
    LiveData<Estate> getEstateFromId(long estateId);

    @Query("SELECT MAX(estateId) FROM Estate")
    LiveData<Integer> getLastEstate();

    @Query("SELECT * FROM Estate ORDER BY estateId DESC LIMIT 1")
    Estate getTheLast();


    @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    Cursor getEstatesWithCursor(long agentId);

    @Insert()
    long insertEstate(Estate estate);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE estateId = :estateId")
    int deleteEstate(long estateId);


}

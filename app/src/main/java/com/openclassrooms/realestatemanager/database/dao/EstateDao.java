package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.openclassrooms.realestatemanager.data.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate ORDER BY estateId DESC")
    LiveData<List<Estate>> getAllEstate();

      @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    LiveData<List<Estate>> getEstatePerAgent(long agentId);



    @Query("SELECT * FROM Estate WHERE estateId = :estateId")
    LiveData<Estate> getEstateFromId(long estateId);

     @Query("SELECT MAX(estateId) FROM Estate")
    LiveData<Integer> getLastEstate();



    @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    Cursor getEstatesWithCursor(long agentId);

    @Insert
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE estateId = :estateId")
    int deleteEstate(long estateId);
}

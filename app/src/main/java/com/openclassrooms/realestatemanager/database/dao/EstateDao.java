package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.data.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    LiveData<List<Estate>> getEstate(long agentId);

    @Insert
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("DELETE FROM Estate WHERE id = :id")
    int deleteEstate(long id);
}

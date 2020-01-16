package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

@Dao
public interface EstateDao {

    @Query("SELECT * FROM Estate ORDER BY estateId DESC")
    LiveData<List<Estate>> getAllEstate();

    @Query("SELECT * FROM Estate WHERE agentId = :agentId")
    LiveData<List<Estate>> getEstatePerAgent(long agentId);



    // test raw query
    @RawQuery(observedEntities = Estate.class)
    LiveData<List<Estate>>getEstateByFilter(SupportSQLiteQuery query);



    //Sold
    @Query("SELECT * FROM Estate WHERE sold ORDER BY price DESC")
    LiveData<List<Estate>> displaySoldEstateDesc();

    @Query("SELECT * FROM Estate WHERE sold ORDER BY price ASC")
    LiveData<List<Estate>> displaySoldEstateAsc();


    @Query("SELECT * FROM Estate WHERE estateId = :estateId")
    LiveData<Estate> getEstateFromId(long estateId);

    @Query("SELECT MAX(estateId) FROM Estate")
    LiveData<Integer> getLastEstate();

    @Query("SELECT * FROM Estate  ORDER BY estateId DESC LIMIT 1")
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

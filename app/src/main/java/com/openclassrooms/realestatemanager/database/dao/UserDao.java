package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createUser(User user);

    @Query("SELECT * FROM User WHERE agentId = :agentId")
    LiveData<User> getUser(long agentId);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUsers();

    @Query("DELETE FROM User WHERE agentId= :agentId")
    int deleteUser(long agentId);

}

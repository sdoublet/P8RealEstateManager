package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.data.User;
import com.openclassrooms.realestatemanager.database.dao.UserDao;

public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    //-------GET USER ---------

    public LiveData<User> getUser(long agentId){return this.userDao.getUser(agentId);}
}

package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.database.dao.UserDao;

import java.util.List;

public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    //-------INSERT USER--------
    public void insertUser(User user){  long userId = userDao.createUser(user);}

    //-------GET USER ---------

    public LiveData<User> getUser(long agentId){return this.userDao.getUser(agentId);}
    public LiveData<List<User>> getAllUsers(){return this.userDao.getUsers();}

    //-------DELETE USER-------
    public void deleteUser(long agentId){ this.userDao.deleteUser(agentId);}
}

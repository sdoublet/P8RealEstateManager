package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long agentId;
    private String name;


    //private int photo;
    private String email;

    @Ignore
    public User() {
    }

    public User(long agentId, String name, String email) {
        this.name = name;
        //this.photo = photo;
        this.email = email;
        this.agentId = agentId;
    }

    public long getAgentId() {
        return agentId;
    }

    public void setId(long agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


   // public int getPhoto() {
   //     return photo;
   // }

   // public void setPhoto(int photo) {
//        this.photo = photo;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

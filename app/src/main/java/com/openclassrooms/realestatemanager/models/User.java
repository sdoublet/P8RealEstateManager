package com.openclassrooms.realestatemanager.models;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long agentId;
    private String name;
    private String surname;
    private String password;
    private String agency;
    private String email;


    @Ignore
    public User() {
    }

    public User(long agentId, String name, String surname, String password, String agency,  String email) {
        this.agentId = agentId;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.agency = agency;
        this.email = email;

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }


}

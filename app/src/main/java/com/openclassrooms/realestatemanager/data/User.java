package com.openclassrooms.realestatemanager.data;

public class User {
    private String name;
    private int photo;
    private String email;

    public User() {
    }

    public User(String name, int photo, String email) {
        this.name = name;
        this.photo = photo;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

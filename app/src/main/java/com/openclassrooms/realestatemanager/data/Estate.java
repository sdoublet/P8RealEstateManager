package com.openclassrooms.realestatemanager.data;

public class Estate {
    private int Id;
    private String type;
    private float price;
    private float surface;
    private int nbRoom;
    private int bedroom;
    private int bathroom;
    private String description;
    private int photo;
    private String address;
    private int postalCode;
    private String city;
    private boolean sold;
    private String entryDate;
    private String soldDate;
    private int agentId;
    private double latitude;
    private double longitude;

    public Estate() {
    }

    public Estate(int id, String type, float price, float surface, int nbRoom, int bedroom, int bathroom, String description, int photo, String address, int postalCode, String city, boolean sold, String entryDate, String soldDate, int agentId, double latitude, double longitude) {
        Id = id;
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.nbRoom = nbRoom;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.description = description;
        this.photo = photo;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.sold = sold;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
        this.agentId = agentId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //GETTERS

    public int getId() {
        return Id;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public float getSurface() {
        return surface;
    }

    public int getNbRoom() {
        return nbRoom;
    }

    public int getBedroom() {
        return bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public String getDescription() {
        return description;
    }

    public int getPhoto() {
        return photo;
    }

    public String getAddress() {
        return address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public boolean isSold() {
        return sold;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public int getAgentId() {
        return agentId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    //SETTERS
    public void setId(int id) {
        Id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public void setNbRoom(int nbRoom) {
        this.nbRoom = nbRoom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

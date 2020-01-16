package com.openclassrooms.realestatemanager.models;


import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "agentId", childColumns = "agentId"))
public class Estate {

    @PrimaryKey(autoGenerate = true)
    private long estateId ;
    private String type;
    private int price;
    private float surface;
    private float surfaceLand;
    private int nbRoom;
    private int bedroom;
    private int bathroom;
    private String description;
    private String heating;
  //  private int photo;
    private String address;
    private int postalCode;
    private String city;
    private boolean sold;
    private String entryDate;
    private String soldDate;
    private long agentId;
    private double latitude;
    private double longitude;
    //Point of interest
    private boolean school;
    private boolean shop;
    private boolean park;
    private boolean hospital;
    private boolean transport;
    private boolean administration;



   public Estate() {
    }



    public Estate(String type, int price, float surface, float surfaceLand, int nbRoom, int bedroom, int bathroom,
                  String description, String heating, String address, int postalCode, String city, boolean sold,
                  String entryDate, String soldDate, long agentId, double latitude, double longitude, boolean school,
                  boolean shop, boolean park, boolean hospital, boolean transport, boolean administration) {

        this.type = type;
        this.price = price;
        this.surface = surface;
        this.surfaceLand = surfaceLand;
        this.nbRoom = nbRoom;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.description = description;
        this.heating = heating;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.sold = sold;
        this.entryDate = entryDate;
        this.soldDate = soldDate;
        this.agentId = agentId;
        this.latitude = latitude;
        this.longitude = longitude;

        this.school = school;
        this.shop = shop;
        this.park = park;
        this.hospital = hospital;
        this.transport = transport;
        this.administration = administration;
    }

    //---------------------------------GETTERS-------------------------------------

    public long getEstateId() {
        return estateId;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
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

    public float getSurfaceLand() {
        return surfaceLand;
    }

    public String getHeating() {
        return heating;
    }

    public boolean isSchool() {
        return school;
    }

    public boolean isShop() {
        return shop;
    }

    public boolean isPark() {
        return park;
    }

    public boolean isHospital() {
        return hospital;
    }

    public boolean isTransport() {
        return transport;
    }

    public boolean isAdministration() {
        return administration;
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

    public long getAgentId() {
        return agentId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    //------------------------------SETTERS-----------------------------------


    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
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

    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSurfaceLand(float surfaceLand) {
        this.surfaceLand = surfaceLand;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public void setSchool(boolean school) {
        this.school = school;
    }

    public void setShop(boolean shop) {
        this.shop = shop;
    }

    public void setPark(boolean park) {
        this.park = park;
    }

    public void setHospital(boolean hospital) {
        this.hospital = hospital;
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }

    public void setAdministration(boolean administration) {
        this.administration = administration;
    }

    //-------------
    //UTILS
    //-------------

 public static Estate fromContentValues(ContentValues contentValues){
    final Estate estate = new Estate();
    if (contentValues.containsKey("estateId")) estate.setEstateId(contentValues.getAsLong("estateId"));
    if (contentValues.containsKey("type")) estate.setType(contentValues.getAsString("type"));
    if (contentValues.containsKey("price")) estate.setPrice(contentValues.getAsInteger("price"));
    if (contentValues.containsKey("surface")) estate.setSurface(contentValues.getAsFloat("surface"));
    if (contentValues.containsKey("nbRoom")) estate.setNbRoom(contentValues.getAsInteger("nbRoom"));
    if (contentValues.containsKey("bedroom")) estate.setBedroom(contentValues.getAsInteger("bedroom"));
    if (contentValues.containsKey("bathroom")) estate.setBathroom(contentValues.getAsInteger("bathroom"));
    if (contentValues.containsKey("description")) estate.setDescription(contentValues.getAsString("description"));
    //if (contentValues.containsKey("photo")) estate.setPhoto(contentValues.getAsInteger("photo"));
    if (contentValues.containsKey("address")) estate.setAddress(contentValues.getAsString("address"));
    if (contentValues.containsKey("postalCode")) estate.setPostalCode(contentValues.getAsInteger("postalCode"));
    if (contentValues.containsKey("city")) estate.setCity(contentValues.getAsString("city"));
    if (contentValues.containsKey("sold")) estate.setSold(contentValues.getAsBoolean("sold"));
    if (contentValues.containsKey("entryDate")) estate.setEntryDate(contentValues.getAsString("entryDate"));
    if (contentValues.containsKey("soldDate")) estate.setSoldDate(contentValues.getAsString("soldDate"));
    if (contentValues.containsKey("agentId")) estate.setAgentId(contentValues.getAsInteger("agentId"));
    if (contentValues.containsKey("latitude")) estate.setLatitude(contentValues.getAsDouble("latitude"));
    if (contentValues.containsKey("longitude")) estate.setLongitude(contentValues.getAsDouble("longitude"));
    return estate;
 }
}

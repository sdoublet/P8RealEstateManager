package com.openclassrooms.realestatemanager.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.util.Converters;

@Entity(foreignKeys = @ForeignKey(entity = Estate.class,
parentColumns = "estateId",
childColumns = "estateId"))
public class Picture {

    @PrimaryKey(autoGenerate = true)
    private long photoId;
    @TypeConverters(Converters.class)
    private Uri uri;
    private String description;
    private long estateId;

    public Picture() {
    }

    public Picture(Uri uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public Picture(Uri uri, String description, long estateId) {
        this.uri = uri;
        this.description = description;
        this.estateId = estateId;
    }

    //--------------------------GETTERS---------------------------
    public Uri getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public long getEstateId() {
        return estateId;
    }

    public long getPhotoId() {
        return photoId;
    }
//------------------------------SETTERS---------------------------

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }


    // converter
    @NonNull
    @Override
    public  String toString(){
        return "Picture{" +
                "photoId=" + photoId +
                ", uri=" + uri +
                ", description=" + description +
                ", estateId=" + estateId +
                '}';

    }
    //---------------POPULATE DATA------------
    public static Picture[] populateDta(){
        return new Picture[]{
                new Picture(Uri.parse("android.ressource://com.openclassrooms.realestatemanager/drawable/country_house"), "country", 1),
                new Picture(Uri.parse("android.ressource://com.openclassrooms.realestatemanager/drawable/manor"), "manor", 1),
                new Picture(Uri.parse("android.ressource://com.openclassrooms.realestatemanager/drawable/modern_house"), "modern", 1),
        };
    }
}



package com.openclassrooms.realestatemanager.models;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Estate.class,
parentColumns = "photoId",
childColumns = "estateId"))
public class Picture {

    @PrimaryKey(autoGenerate = true)
    private long photoId;
    private Uri uri;
    private String description;
    private long estateId;

    public Picture() {
    }

    public Picture(Uri uri, String description) {
        this.uri = uri;
        this.description = description;
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
}

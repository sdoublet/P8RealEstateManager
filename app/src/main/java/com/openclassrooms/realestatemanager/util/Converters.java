package com.openclassrooms.realestatemanager.util;

import android.net.Uri;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public static String fromUri(Uri value){
        return value.toString();
    }

    @TypeConverter
    public static Uri fromString(String value){
        return value == null ? null : Uri.parse(value);
    }
}

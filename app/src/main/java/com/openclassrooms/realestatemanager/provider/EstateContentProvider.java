package com.openclassrooms.realestatemanager.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.database.database.RoomDb;

public class EstateContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.provider";
    public static final String TABLE_NAME = Estate.class.getSimpleName();
    public static final Uri URI_ESTATE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext()!=null){
            long userId = ContentUris.parseId(uri);
            final Cursor cursor = RoomDb.getInstance(getContext()).estateDao().getEstatesWithCursor(userId);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.estate/" + AUTHORITY + "," + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (getContext()!=null){
            final long id = RoomDb.getInstance(getContext()).estateDao().insertEstate(Estate.fromContentValues(values));
            if (id!=0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext()!=null){
            final int count = RoomDb.getInstance(getContext()).estateDao().deleteEstate(ContentUris.parseId(uri));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to delete row into "+ uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
       if (getContext()!=null){
           final int count = RoomDb.getInstance(getContext()).estateDao().updateEstate(Estate.fromContentValues(values));
           getContext().getContentResolver().notifyChange(uri, null);
           return count;
       }
       throw new IllegalArgumentException("failed to update row into " + uri);
    }
}

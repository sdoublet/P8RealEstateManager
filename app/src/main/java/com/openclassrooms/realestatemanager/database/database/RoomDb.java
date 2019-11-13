package com.openclassrooms.realestatemanager.database.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.data.Estate;
import com.openclassrooms.realestatemanager.data.User;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.database.dao.UserDao;

@Database(entities = {User.class, Estate.class}, version = 1, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    //SINGLETON
    private static volatile RoomDb INSTANCE;

    //DAO
    public abstract UserDao userDao();
    public abstract EstateDao estateDao();

    //INSTANCE
    public static RoomDb getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RoomDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDb.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                // TODO: 13/11/2019 put some values since user.
            }
        };
    }
}

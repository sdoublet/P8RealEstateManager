package com.openclassrooms.realestatemanager.database.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.PictureDao;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.database.dao.EstateDao;
import com.openclassrooms.realestatemanager.database.dao.UserDao;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Estate.class, Picture.class}, version = 3, exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    //SINGLETON
    private static volatile RoomDb INSTANCE;

    //DAO
    public abstract UserDao userDao();
    public abstract EstateDao estateDao();
    public abstract PictureDao pictureDao();

    //INSTANCE
    public static RoomDb getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RoomDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDb.class, "MyDatabase.db")
                            //.fallbackToDestructiveMigration()
                            .addCallback(prepopulateDatabase())
                            .addCallback(prepopulateDatabaseFromEstate())
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
                contentValues.put("agentId", 1);
                contentValues.put("name", "seb");
                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }

    private static Callback prepopulateDatabaseWithPicture(Context context){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(()->
                        getInstance(context).pictureDao().insertAllPicture(Picture.populateDta()));
            }
        };
    }

    private static Callback prepopulateDatabaseFromEstate(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("estateId", 1);
                contentValues.put("type", "House");
                contentValues.put("price", 250000);
                contentValues.put("surface", 125);
                contentValues.put("surfaceLand", 1500);
                contentValues.put("nbRoom", 10);
                contentValues.put("bedroom", 5);
                contentValues.put("bathroom", 2);
                contentValues.put("description", "superbe maison");
                contentValues.put("heating", "fireWood");
                contentValues.put("postalCode", 39380);
                contentValues.put("address", "19 rue du bois");
                contentValues.put("city", "chamblay");
                contentValues.put("sold", false);
                contentValues.put("entryDate","today" );
                contentValues.put("soldDate", "today");
                contentValues.put("agentId", 1);
                contentValues.put("latitude", 4.25555);
                contentValues.put("longitude", 41.25555);
                contentValues.put("school", true);
                contentValues.put("shop", true);
                contentValues.put("park", false);
                contentValues.put("hospital", false);
                contentValues.put("transport", false);
                contentValues.put("administration", true);
                db.insert("Estate", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}

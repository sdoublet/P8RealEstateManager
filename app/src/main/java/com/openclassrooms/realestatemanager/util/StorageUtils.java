package com.openclassrooms.realestatemanager.util;

import android.os.Environment;
import java.io.File;


public class StorageUtils {

    private static File createOrGetFile(File destination, String fileName, String folderName) {
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }



    //---------------------------
    //EXTERNAL STORAGE
    //---------------------------


    // Check if external storage is available and if we could read or write on

    public static boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state));
    }

    public static boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state))||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}

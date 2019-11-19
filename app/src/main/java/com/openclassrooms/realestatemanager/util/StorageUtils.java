package com.openclassrooms.realestatemanager.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class StorageUtils {

    private static File createOrGetFile(File destination, String fileName, String folderName) {
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    //------------------------------
    //READ AND WRITE ON STORAGE
    //------------------------------

    private static String readOnFile(Context context, File file) {
        String result = null;
        if (file.exists()) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }
                    result = sb.toString();
                } finally {
                    br.close();
                }
            } catch (IOException e) {
                Toast.makeText(context, "erroe", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }

    private static void writeOnFile(Context context, String text, File file) {
        try {
            file.getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            try {
                writer.write(text);
                writer.flush();
                fileOutputStream.getFD().sync();
            } finally {
                writer.close();
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(context, "error hapened", Toast.LENGTH_SHORT).show();
        }
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

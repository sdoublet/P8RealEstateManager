package com.openclassrooms.realestatemanager.util;

import android.content.Context;
import android.content.res.Configuration;

public class CheckTabletDevice {

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isLand(Context context){
        return (context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE);
    }
}

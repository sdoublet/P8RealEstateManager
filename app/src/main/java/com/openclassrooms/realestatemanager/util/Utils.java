package com.openclassrooms.realestatemanager.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.api.apiUsd.ApiUsd;
import com.openclassrooms.realestatemanager.feature.getCurrentDollarValue.DollarStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    private Disposable disposable;

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * MoneyPref.getInstance().getDollar());
    }

    // convert euros to dollars
    public static int convertEuroToDollar(int euros){return (int)Math.round(euros/MoneyPref.getInstance().getDollar());}

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }

    // convert to the good format
    public static String convertDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }
    //-----------------
    //CONNECTION
    //-----------------
    //check connection
    public static void onCheckNetworkStatus(Context context, View view){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){
            informationUser(view,"connected to Internet...");
        }else {
            informationUser(view,"No internet connection available...");
        }
    }

    public static void checkNetworkType(Context context, View view){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiInfo.isConnected()){
            informationUser(view,"Connected by WiFi");
        }else if (mobileInfo.isConnected()){
            informationUser(view,"Connected to Mobile data");
        }else {
            informationUser(view,"No network available");
        }
    }
    //for user
    public static void informationUser(View view,String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    //-----------------
    //String format
    //-----------------
    public  static String stringFormat(double d){
     return String.format("%s", d);
    }

    //------------------
    //Get local country
    //-----------------
    public static String localeCountry(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0).getCountry();
        }else {
            return context.getResources().getConfiguration().locale.getCountry();
        }
    }


}

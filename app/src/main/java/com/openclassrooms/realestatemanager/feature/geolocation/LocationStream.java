package com.openclassrooms.realestatemanager.feature.geolocation;

import com.openclassrooms.realestatemanager.api.ApiGeocoding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocationStream {

    private static LocationServices locationServices = LocationServices.retrofit.create(LocationServices.class);

    public static Observable<ApiGeocoding> streamFetchGeocoding(String address, String apiKey){
        return locationServices.getGeocoding(address, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

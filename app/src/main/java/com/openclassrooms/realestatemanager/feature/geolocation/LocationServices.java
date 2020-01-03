package com.openclassrooms.realestatemanager.feature.geolocation;

import com.openclassrooms.realestatemanager.api.googleMap.ApiGeocoding;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationServices {

    @GET("maps/api/geocode/json?")
    Observable<ApiGeocoding> getGeocoding(@Query("address")String address,
                                          @Query("key")String key);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

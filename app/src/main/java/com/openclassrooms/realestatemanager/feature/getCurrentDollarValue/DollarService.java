package com.openclassrooms.realestatemanager.feature.getCurrentDollarValue;

import com.openclassrooms.realestatemanager.api.apiUsd.ApiUsd;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface DollarService {

    @GET("/latest?base=USD")
    Observable<ApiUsd> getApiUsd ();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

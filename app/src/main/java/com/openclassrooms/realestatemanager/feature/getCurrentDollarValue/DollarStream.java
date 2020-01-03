package com.openclassrooms.realestatemanager.feature.getCurrentDollarValue;


import com.openclassrooms.realestatemanager.api.apiUsd.ApiUsd;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DollarStream {

    private static DollarService dollarService = DollarService.retrofit.create(DollarService.class);


    public static Observable<ApiUsd> streamFetchApiUsd(){
      return dollarService.getApiUsd()
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .timeout(10, TimeUnit.SECONDS);
    }

}

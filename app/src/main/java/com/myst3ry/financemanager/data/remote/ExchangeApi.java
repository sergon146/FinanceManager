package com.myst3ry.financemanager.data.remote;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeApi {

    String API_BASE_URL = "https://free.currencyconverterapi.com/api/v6/";

    @GET("convert?compact=ultra")
    Flowable<ResponseBody> getActualExchangeRates(@Query("q") String fromTo);
}

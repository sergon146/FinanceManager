package com.myst3ry.financemanager.data.remote;

import com.myst3ry.financemanager.data.remote.model.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface ExchangeApi {

    String API_BASE_URL = "https://www.cbr-xml-daily.ru/";

    @GET("daily_json.js")
    Observable<ApiResponse> getActualExchangeRates();
}

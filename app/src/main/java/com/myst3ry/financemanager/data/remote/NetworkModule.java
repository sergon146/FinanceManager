package com.myst3ry.financemanager.data.remote;

import com.myst3ry.financemanager.utils.rx.RxThreadCallAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ExchangeApi.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new RxThreadCallAdapter(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    ExchangeApi providesExchangeRatesApi(final Retrofit retrofit) {
        return retrofit.create(ExchangeApi.class);
    }
}

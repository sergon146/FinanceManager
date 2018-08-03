package com.myst3ry.financemanager.ui.main;

import android.content.Context;

import com.myst3ry.financemanager.data.local.RatesStorage;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.data.remote.model.Valute;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class MainModel {

    @Inject
    ExchangeApi api;

    private Context context;

    public MainModel(final Context context) {
        this.context = context;
    }

    public void requestActualExchangeRates(final CompositeDisposable disposables, final OnErrorCallback callback) {
        disposables.add(api.getActualExchangeRates()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribe(response -> saveActualExchangeRates(response.getValutes()), callback::onError));
    }

    private void saveActualExchangeRates(final Valute valutes) {
        RatesStorage.getInstance().saveUsdRate(context, valutes != null ? valutes.getUSD().getValue() : 0f);
    }

    public interface OnErrorCallback {
        void onError(final Throwable throwable);
    }
}


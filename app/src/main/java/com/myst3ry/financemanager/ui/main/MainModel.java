package com.myst3ry.financemanager.ui.main;

import android.content.Context;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.FinanceManagerApp;
import com.myst3ry.financemanager.data.local.AccountsDbStub;
import com.myst3ry.financemanager.data.local.RatesStorage;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.data.remote.model.Valute;

import java.math.BigDecimal;
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
        FinanceManagerApp.getNetworkComponent(this.context).inject(this);
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

    public void initAccounts() {
        final AccountsDbStub database = AccountsDbStub.getInstance();
        database.addAccount(Account.newBuilder()
                .setTitle("Наличные")
                .setBalance(new BigDecimal(50000))
                .setCurrencyType(CurrencyType.RUR)
                .setAccountType(AccountType.CASH)
                .build());

        database.addAccount(Account.newBuilder()
                .setTitle("Кредитная карта")
                .setBalance(new BigDecimal(60000))
                .setCurrencyType(CurrencyType.RUR)
                .setAccountType(AccountType.CREDIT)
                .build());
    }

    public interface OnErrorCallback {
        void onError(final Throwable throwable);
    }
}


package com.myst3ry.financemanager;

import android.app.Application;
import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.myst3ry.financemanager.network.DaggerNetworkComponent;
import com.myst3ry.financemanager.network.NetworkComponent;
import com.myst3ry.financemanager.network.NetworkModule;
import com.myst3ry.financemanager.utils.LocaleManager;

public final class FinanceManagerApp extends Application {

    private static final String USD_RATE = BuildConfig.APPLICATION_ID + "USD_RATE";

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleManager.setLocale(this);
        prepareDaggerComponents();
    }

    private void prepareDaggerComponents() {
        final NetworkModule networkModule = new NetworkModule();
        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(networkModule)
                .build();
    }

    public static NetworkComponent getNetworkComponent(final Context context) {
        return ((FinanceManagerApp) context.getApplicationContext()).mNetworkComponent;
    }

    public void saveRates(final Float... rates) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat(USD_RATE, rates[0]).apply();
    }

    public Float getSavedRates() {
        return PreferenceManager.getDefaultSharedPreferences(this).getFloat(USD_RATE, 0f);
    }
}

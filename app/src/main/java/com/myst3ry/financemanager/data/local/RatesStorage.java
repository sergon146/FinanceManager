package com.myst3ry.financemanager.data.local;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.myst3ry.financemanager.BuildConfig;

public final class RatesStorage {

    private static final String USD_RATE = BuildConfig.APPLICATION_ID + "USD_RATE";

    private static volatile RatesStorage INSTANCE;

    public static RatesStorage getInstance() {
        RatesStorage instance = INSTANCE;
        if (instance == null) {
            synchronized (RatesStorage.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new RatesStorage();
                }
            }
        }
        return instance;
    }

    public void saveUsdRate(final Context context, final Float rate) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putFloat(USD_RATE, rate).apply();
    }

    public Float getUsdRate(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getFloat(USD_RATE, 0f);
    }
}

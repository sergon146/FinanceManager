package com.myst3ry.financemanager;

import android.app.Application;

import com.myst3ry.financemanager.utils.LocaleManager;

public final class FinanceManagerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleManager.setLocale(this);
    }
}

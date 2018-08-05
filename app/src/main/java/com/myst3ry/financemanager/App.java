package com.myst3ry.financemanager;

import android.app.Activity;
import android.app.Application;

import com.myst3ry.financemanager.data.local.MainDatabase;
import com.myst3ry.financemanager.di.base.AppInjector;
import com.myst3ry.financemanager.utils.LocaleManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public final class App extends Application implements HasActivityInjector {
    private static App instance;
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    private MainDatabase database;

    public static App getInstance() {
        return instance;
    }

    public MainDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppInjector.init(this);
        LocaleManager.setLocale(this);
        initDatabase();
    }

    private void initDatabase() {
        database = MainDatabase.getInstance(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}

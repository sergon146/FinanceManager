package com.myst3ry.financemanager;

import android.app.Activity;
import android.app.Application;

import com.myst3ry.financemanager.di.base.AppInjector;
import com.myst3ry.financemanager.di.component.AppComponent;
import com.myst3ry.financemanager.utils.LocaleManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public final class App extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    private AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return AppInjector.getAppComponent();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
        LocaleManager.setLocale(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}

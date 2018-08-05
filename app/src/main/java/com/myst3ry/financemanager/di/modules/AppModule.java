package com.myst3ry.financemanager.di.modules;

import android.app.Application;
import android.content.Context;

import com.myst3ry.financemanager.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static Context provideContext(Application app) {
        return app;
    }

    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity contributeMainActivity();
}

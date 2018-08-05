package com.myst3ry.financemanager.di.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.myst3ry.financemanager.App;
import com.myst3ry.financemanager.di.component.AppComponent;
import com.myst3ry.financemanager.di.component.DaggerAppComponent;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class AppInjector {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void init(App app) {
        appComponent = DaggerAppComponent.builder()
                .application(app)
                .build();

        appComponent.inject(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity)
                    .getSupportFragmentManager().registerFragmentLifecycleCallbacks(
                    new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentAttached(final FragmentManager fm,
                                                       final Fragment f,
                                                       final Context context) {

                            if (f instanceof Injectable) {
                                AndroidSupportInjection.inject(f);
                            }
                        }

                        @Override
                        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                            super.onFragmentDestroyed(fm, f);
                        }
                    }, true);
        }
    }
}

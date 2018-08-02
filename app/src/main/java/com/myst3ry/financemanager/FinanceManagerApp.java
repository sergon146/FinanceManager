package com.myst3ry.financemanager;

import android.app.Application;
import android.content.Context;

import com.myst3ry.financemanager.data.remote.DaggerNetworkComponent;
import com.myst3ry.financemanager.data.remote.NetworkComponent;
import com.myst3ry.financemanager.data.remote.NetworkModule;
import com.myst3ry.financemanager.utils.LocaleManager;

public final class FinanceManagerApp extends Application {

    private NetworkComponent networkComponent;

    public static NetworkComponent getNetworkComponent(final Context context) {
        return ((FinanceManagerApp) context.getApplicationContext()).networkComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleManager.setLocale(this);
        prepareDaggerComponents();
    }

    private void prepareDaggerComponents() {
        final NetworkModule networkModule = new NetworkModule();
        networkComponent = DaggerNetworkComponent.builder()
                .networkModule(networkModule)
                .build();
    }
}

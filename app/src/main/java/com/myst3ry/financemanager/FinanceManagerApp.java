package com.myst3ry.financemanager;

import android.app.Application;
import android.content.Context;

import com.myst3ry.financemanager.network.DaggerNetworkComponent;
import com.myst3ry.financemanager.network.NetworkComponent;
import com.myst3ry.financemanager.network.NetworkModule;
import com.myst3ry.financemanager.utils.LocaleManager;

public final class FinanceManagerApp extends Application {

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
}

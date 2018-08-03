package com.myst3ry.financemanager.di.component;

import android.app.Application;

import com.myst3ry.financemanager.App;
import com.myst3ry.financemanager.di.modules.NetworkModule;
import com.myst3ry.financemanager.di.modules.AppModule;
import com.myst3ry.financemanager.di.modules.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(App instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}

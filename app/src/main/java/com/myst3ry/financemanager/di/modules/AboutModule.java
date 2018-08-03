package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.ui.about.AboutPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AboutModule {

    @Provides
    static AboutPresenter provideAboutPresenter() {
        return new AboutPresenter();
    }
}

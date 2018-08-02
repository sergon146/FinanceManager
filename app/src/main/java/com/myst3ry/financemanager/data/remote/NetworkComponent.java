package com.myst3ry.financemanager.data.remote;

import com.myst3ry.financemanager.ui.main.MainModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    void inject(final MainModel mainModel);
}
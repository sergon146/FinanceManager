package com.myst3ry.financemanager.network;

import com.myst3ry.financemanager.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {

    void inject(final MainActivity mainActivity);
}
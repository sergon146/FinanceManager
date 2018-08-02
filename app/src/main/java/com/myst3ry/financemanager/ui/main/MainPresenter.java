package com.myst3ry.financemanager.ui.main;

import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public final class MainPresenter extends BasePresenter<MainView> {

    private MainModel mMainModel;

    public MainPresenter(final MainModel model) {
        this.mMainModel = model;
    }

    public void getActualExchangeRates(final CompositeDisposable disposables) {
        view.showProgressBar();
        mMainModel.requestActualExchangeRates(disposables, this::onError);
        view.hideProgressBar();
    }

    public void onError(final Throwable throwable) {
        if (throwable instanceof HttpException) {
            final HttpException http = ((HttpException) throwable);
            view.showError(http.code(), http.getLocalizedMessage());
        } else {
            view.showConnectionError();
        }
    }

    public void initAccounts() {
        mMainModel.initAccounts();
    }

    public void onTabClicked(int position, boolean wasSelected) {
        if (wasSelected) {
            //            getRouter().goBackToRoot();
            return;
        }
        Screens screen;
        switch (TabBarScreens.values()[position]) {
            case MAIN:
                screen = Screens.MAIN_SCREEN;
                break;
            case FEED:
                screen = Screens.FEED_SCREEN;
                break;
            case REPORT:
                screen = Screens.REPORT_SCREEN;
                break;
            case SETTINGS:
                screen = Screens.SETTINGS_SCREEN;
                break;
            default:
                screen = Screens.MAIN_SCREEN;
                break;
        }

        view.activateTab(screen);
    }
}

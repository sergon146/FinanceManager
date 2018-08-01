package com.myst3ry.financemanager.ui.activity.main;

import com.myst3ry.financemanager.ui.activity.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public final class MainPresenter extends BasePresenter<MainView> {

    private MainModel mMainModel;

    public MainPresenter(final MainModel model) {
        this.mMainModel = model;
    }

    public void getActualExchangeRates(final CompositeDisposable disposables) {
        mView.showProgressBar();
        mMainModel.requestActualExchangeRates(disposables, this::onError);
        mView.hideProgressBar();
        mView.showUI();
    }

    public void onError(final Throwable throwable) {
        if (throwable instanceof HttpException) {
            final HttpException http = ((HttpException) throwable);
            mView.showError(http.code(), http.getLocalizedMessage());
        } else {
            mView.showConnectionError();
        }
    }

    public void initAccounts() {
        mMainModel.initAccounts();
    }
}

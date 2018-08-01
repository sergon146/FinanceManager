package com.myst3ry.financemanager.ui.activity;

public abstract class BasePresenter<T> {

    protected T mView;

    public void attachView(T view) {
        this.mView = view;
    }

    public void detachView() {
        this.mView = null;
    }
}

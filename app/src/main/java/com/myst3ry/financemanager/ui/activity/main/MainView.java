package com.myst3ry.financemanager.ui.activity.main;

public interface MainView {

    void showUI();

    void showProgressBar();

    void hideProgressBar();

    void showError(final int code, final String message);

    void showConnectionError();
}

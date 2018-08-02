package com.myst3ry.financemanager.ui.main;

import com.myst3ry.financemanager.ui.main.screens.Screens;

public interface MainView {
    void showProgressBar();

    void hideProgressBar();

    void showError(final int code, final String message);

    void showConnectionError();

    void activateTab(Screens screen);
}

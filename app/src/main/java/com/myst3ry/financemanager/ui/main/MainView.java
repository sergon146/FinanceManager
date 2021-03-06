package com.myst3ry.financemanager.ui.main;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.financemanager.ui.main.screens.Screens;

public interface MainView extends BaseView {
    void showProgressBar();

    void hideProgressBar();

    void showError(final int code, final String message);

    void showConnectionError();

    void activateTab(Screens screen, boolean wasSelected);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void openingScreen(Screens screen, Object data, boolean isParent);
}

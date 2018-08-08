package com.myst3ry.financemanager.ui.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.myst3ry.financemanager.ui.main.screens.Screens;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface BaseView extends MvpView {
    void showToast(@StringRes int resId);

    void showToast(String text);

    void showLongToast(int resId);

    void showLongToast(String message);

    void showProgressBar();

    void hideProgressBar();

    void setScreenTitle(int titleId);

    void setScreenTitle(String title);

    void openScreen(Screens screen, Object data);

    @StateStrategyType(SkipStrategy.class)
    void openScreen(Screens screen, Object data, boolean isRoot);
}

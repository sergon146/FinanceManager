package com.myst3ry.financemanager.ui.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface BaseView extends MvpView {
    void showToast(@StringRes int resId);

    void showToast(String text);

    void showLongToast(int resId);

    void showLongToast(String message);
}

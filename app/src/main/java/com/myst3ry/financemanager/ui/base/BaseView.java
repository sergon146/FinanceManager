package com.myst3ry.financemanager.ui.base;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {
    void showToast(@StringRes int resId);

    void showToast(String text);

    void showLongToast(int resId);

    void showLongToast(String message);
}

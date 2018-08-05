package com.myst3ry.financemanager.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.myst3ry.financemanager.di.base.Injectable;
import com.myst3ry.financemanager.ui.main.screens.Screens;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import icepick.Icepick;

public abstract class BaseFragment<Presenter extends BasePresenter> extends MvpAppCompatFragment
        implements BaseView, HasSupportFragmentInjector, Injectable {
    protected BaseActivity activity;
    protected Presenter presenter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    protected Presenter providePresenter() {
        return null;
    }

    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = providePresenter();
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        prepareViews();
    }

    protected void prepareViews() {
    }

    protected void openScreen(Screens screen, Object data) {
        activity.openScreen(screen, data, false);
    }

    protected void openScreen(Screens screen, Object data, boolean isRoot) {
        activity.openScreen(screen, data, isRoot);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    public void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        final View view = getActivity().getCurrentFocus();
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(int resId) {
        showLongToast(getString(resId));
    }

    @Override
    public void showLongToast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void setScreenTitle(@StringRes int resId) {
        setScreenTitle(getString(resId));
    }

    protected void setScreenTitle(String title) {
        activity.setScreenTitle(title);
    }

    protected final void hideScreenTitle() {
        activity.hideScreenTitle();
    }

    public abstract String getScreenTag();

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}

package com.myst3ry.financemanager.ui.base.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.di.base.Injectable;
import com.myst3ry.financemanager.ui.base.BaseActivity;
import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.financemanager.ui.main.screens.Screens;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseDialogMvpFragment<Presenter extends BaseDialogPresenter>
        extends MvpAppCompatDialogFragment
        implements BaseView, HasSupportFragmentInjector, Injectable {

    protected static String LOG_TAG = "";

    @Nullable
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Presenter presenter;

    protected Presenter providePresenter() {
        return null;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    private BaseActivity activity;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG_TAG = this.getClass().getName();
        presenter = providePresenter();
        activity = (BaseActivity) getActivity();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        switch (style) {
            case STYLE_NO_TITLE:
                Window window = getDialog().getWindow();
                if (window != null) {
                    window.requestFeature(Window.FEATURE_NO_TITLE);
                }
                break;
            default:
        }
        super.setupDialog(dialog, style);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().hide();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLongToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setScreenTitle(@StringRes int resId) {
        setScreenTitle(getString(resId));
    }

    @Override
    public void setScreenTitle(String title) {
        activity.setScreenTitle(title);
    }

    public void hideScreenTitle() {
        activity.hideScreenTitle();
    }



    @Override
    public final void openScreen(Screens screen, Object data) {
        activity.openScreen(screen, data, false);
    }

    @Override
    public final void openScreen(Screens screen, Object data, boolean isRoot) {
        activity.openScreen(screen, data, isRoot);
    }

}
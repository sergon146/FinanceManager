package com.myst3ry.financemanager.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.main.screens.Screens;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity<Presenter extends BasePresenter> extends MvpAppCompatActivity
        implements BaseView, HasSupportFragmentInjector {

    @Nullable
    @BindView(R.id.toolbar_title)
    protected TextView title;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    private Presenter presenter;

    protected Presenter providePresenter() {
        return null;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = providePresenter();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void setScreenTitle(@StringRes int titleId) {
        setScreenTitle(getString(titleId));
    }

    public void setScreenTitle(String titleText) {
        if (title == null) {
            return;
        }

        title.setVisibility(View.VISIBLE);
        if (title != null) {
            title.setText(titleText);
        }
    }

    public abstract void openScreen(Screens screen, Object data, boolean isRoot);

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongToast(int resId) {
        showLongToast(getString(resId));
    }

    @Override
    public void showLongToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void hideScreenTitle() {
        title.setVisibility(View.GONE);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}

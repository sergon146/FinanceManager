package com.myst3ry.financemanager.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.about.AboutFragment;
import com.myst3ry.financemanager.ui.accounts.AccountFragment;
import com.myst3ry.financemanager.ui.balance.BalanceFragment;
import com.myst3ry.financemanager.ui.base.BaseActivity;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;
import com.myst3ry.financemanager.ui.settings.SettingsFragment;
import com.myst3ry.financemanager.ui.transactions.TransactionCreateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public final class MainActivity extends BaseActivity
        implements MainView, FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tab_bar)
    AHBottomNavigation tabBar;
    @BindView(R.id.back)
    View back;

    private MainPresenter presenter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
    }

    @Override
    protected void prepareView() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        shouldDisplayBack();
        prepareTabBar();
    }

    private void prepareTabBar() {
        List<AHBottomNavigationItem> items = new ArrayList<>();

        for (TabBarScreens screen : TabBarScreens.values()) {
            items.add(new AHBottomNavigationItem(getString(screen.getTitle()), screen.getIcon()));
        }

        tabBar.addItems(items);
        tabBar.setBehaviorTranslationEnabled(false);
        tabBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        tabBar.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.charcoal_grey));
        tabBar.setAccentColor(ContextCompat.getColor(this, R.color.color_primary_dark));
        tabBar.setInactiveColor(ContextCompat.getColor(this, R.color.white_50));
        tabBar.setNotificationBackgroundColorResource(R.color.color_primary_dark);
        tabBar.setOnTabSelectedListener((position, wasSelected) -> {
            presenter.onTabClicked(position, wasSelected);
            return false;
        });

        activateTab(Screens.MAIN_SCREEN);
    }

    @Override
    public void activateTab(Screens screen) {
        TabBarScreens tab = screen.tab;
        int pos = tab.ordinal();
        tabBar.setCurrentItem(pos, false);
        tabBar.invalidate();
        openScreen(screen, true);
    }

    @Override
    public void openScreen(Screens screen, boolean isRoot) {
        Fragment fragment;
        switch (screen) {
            case MAIN_SCREEN:
                fragment = AccountFragment.newInstance();
                break;
            case FEED_SCREEN:
                fragment = BalanceFragment.newInstance();
                break;
            case REPORT_SCREEN:
                fragment = AboutFragment.newInstance();
                break;
            case SETTINGS_SCREEN:
                fragment = SettingsFragment.newInstance();
                break;
            case TRANSACTIONS_SCREEN:
                fragment = TransactionCreateFragment.newInstance(0);
                break;
            default:
                throw new RuntimeException("Unknown screen");
        }

        if (isRoot) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
    }

    private void initPresenter() {
        presenter = new MainPresenter(new MainModel(this));
        presenter.attachView(this);
        presenter.initAccounts();
    }

    public void showProgressBar() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showError(final int code, final String message) {
        showToast(String.format(getString(R.string.http_error), code, message));
    }

    public void showConnectionError() {
        showToast(getString(R.string.connection_error));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDestroyed()) {
            presenter.detachView();
        }
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayBack();
    }

    @Override
    public void onBackPressed() {
        int entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    protected void activateScreen(String name) {
        FragmentManager fm = fragmentManager;
        fm.executePendingTransactions();

        // display only root screen in tab
        Screens screen = Screens.getEnum(fm.getBackStackEntryAt(0).getName());
        if (screen != null) {
            tabBar.postDelayed(() -> activateTab(screen), 100);
        }
    }

    public void shouldDisplayBack() {
        boolean canBack = fragmentManager.getBackStackEntryCount() > 1;
        back.setVisibility(canBack ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.back)
    void onBackClick() {
        fragmentManager.popBackStack();
    }
}

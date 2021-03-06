package com.myst3ry.financemanager.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.about.AboutFragment;
import com.myst3ry.financemanager.ui.accounts.AccountsFragment;
import com.myst3ry.financemanager.ui.base.BaseActivity;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;
import com.myst3ry.financemanager.ui.operationslist.OperationListFragment;
import com.myst3ry.financemanager.ui.report.ReportFragment;
import com.myst3ry.financemanager.ui.settings.SettingsFragment;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountItemType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public final class MainActivity extends BaseActivity<MainPresenter>
        implements MainView, FragmentManager.OnBackStackChangedListener {

    @Inject
    @InjectPresenter
    public MainPresenter presenter;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tab_bar)
    AHBottomNavigation tabBar;
    @BindView(R.id.back)
    View back;
    private FragmentManager fragmentManager;

    @Override
    @ProvidePresenter
    protected MainPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareView(savedInstanceState);
    }

    protected void prepareView(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        shouldDisplayBack();
        prepareTabBar(savedInstanceState);
    }

    private void prepareTabBar(Bundle savedInstanceState) {
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

        if (savedInstanceState == null) {
            activateTab(Screens.MAIN_SCREEN, false);
        }
    }

    @Override
    public void activateTab(Screens screen, boolean wasSelected) {
        if (wasSelected && fragmentManager.getBackStackEntryCount() == 1) {
            return;
        }

        invalidateTabBar(screen);
        if ((screen == Screens.ABOUT_SCREEN || screen == Screens.SETTINGS_SCREEN
                || screen == Screens.REPORT_SCREEN) && isTabletUi) {
            openScreen(screen, null, false);
        } else {
            openScreen(screen, null, true);
        }
    }

    private void invalidateTabBar(Screens screen) {
        TabBarScreens tab = screen.tab;
        int pos = tab.ordinal();
        tabBar.setCurrentItem(pos, false);
        tabBar.invalidate();
    }

    @Override
    public void openScreen(Screens screen, Object data) {
        openScreen(screen, data, false);
    }

    @Override
    public void openScreen(Screens screen, Object data, boolean isParent) {
        getPresenter().openScreen(screen, data, isParent);
        invalidateTabBar(screen);
    }

    @Override
    public void openingScreen(Screens screen, Object data, boolean isParent) {
        Fragment fragment;
        switch (screen) {
            case MAIN_SCREEN:
                fragment = AccountsFragment.newInstance();
                break;
            case ABOUT_SCREEN:
                fragment = AboutFragment.newInstance();
                break;
            case REPORT_SCREEN:
                fragment = ReportFragment.newInstance();
                break;
            case OPERATION_LIST_SCREEN:
                if (data instanceof Account) {
                    Account account = (Account) data;
                    fragment = OperationListFragment.newInstance(account.getId());
                } else {
                    AccountItemType type = (AccountItemType) data;
                    fragment = OperationListFragment.newInstance(type);
                }
                break;
            case SETTINGS_SCREEN:
                fragment = SettingsFragment.newInstance();
                break;
            default:
                throw new RuntimeException("Unknown screen");
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isTabletUi) {
            int containerId = isParent ? R.id.container : R.id.container_child;
            transaction.replace(containerId, fragment);
        } else {
            if (isParent) {
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    transaction.setCustomAnimations(
                            R.anim.slide_in_left, R.anim.slide_out_right);
                }
                transaction.replace(R.id.container, fragment)
                        .addToBackStack(screen.name());

                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            } else {
                transaction.setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(R.id.container, fragment)
                        .addToBackStack(screen.name());
            }
        }

        transaction.commit();
    }

    @Override
    public void showProgressBar() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
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

    public void shouldDisplayBack() {
        boolean canBack = fragmentManager.getBackStackEntryCount() > 1;
        back.setVisibility(canBack ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.back)
    void onBackClick() {
        fragmentManager.popBackStack();
    }
}

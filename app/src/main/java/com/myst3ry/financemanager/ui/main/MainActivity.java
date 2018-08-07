package com.myst3ry.financemanager.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.myst3ry.financemanager.ui.balance.BalanceFragment;
import com.myst3ry.financemanager.ui.base.BaseActivity;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;
import com.myst3ry.financemanager.ui.operations.OperationCreateFragment;
import com.myst3ry.financemanager.ui.operationslist.OperationListFragment;
import com.myst3ry.financemanager.ui.settings.SettingsFragment;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountBaseItem;

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
    private boolean isTabletUi;

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
        isTabletUi = getResources().getBoolean(R.bool.is_tablet_ui);
    }

    protected void prepareView(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        shouldDisplayBack();
        prepareTabBar(savedInstanceState);
    }

    private void prepareTabBar(Bundle savedInstanceState) {
        List<AHBottomNavigationItem> items = new ArrayList<>();

        for (TabBarScreens screen: TabBarScreens.values()) {
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
            presenter.onTabClicked(position);
            return false;
        });

        if (savedInstanceState == null) {
            activateTab(Screens.MAIN_SCREEN);
        }
    }

    @Override
    public void activateTab(Screens screen) {
        TabBarScreens tab = screen.tab;
        int pos = tab.ordinal();
        tabBar.setCurrentItem(pos, false);
        tabBar.invalidate();
        openScreen(screen, null, true);
    }

    @Override
    public void openScreen(Screens screen, Object data, boolean isParent) {
        Fragment fragment;
        switch (screen) {
            case MAIN_SCREEN:
                fragment = AccountsFragment.newInstance();
                break;
            case BALANCE_SCREEN:
                Account account = (Account) data;
                fragment = BalanceFragment.newInstance(account.getId());
                break;
            case REPORT_SCREEN:
                fragment = AboutFragment.newInstance();
                break;
            case PERIODIC_SCREEN:
            case FEED_SCREEN:
            case PATTERNS_SCREEN:
                AccountBaseItem item = (Account) data;
                fragment = OperationListFragment.newInstance(item);
                break;
            case SETTINGS_SCREEN:
                fragment = SettingsFragment.newInstance();
                break;
            case CREATE_OPERATIONS_SCREEN:
                long id = (long) data;
                fragment = OperationCreateFragment.newInstance(id);
                break;
            default:
                throw new RuntimeException("Unknown screen");
        }

        int containerId;

        if (isTabletUi) {
            containerId = isParent ? R.id.container : R.id.container_child;
        } else {
            if (isParent) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            containerId = R.id.container;
        }

        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.getTag())
                .commit();
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

package com.myst3ry.financemanager.ui.main.screens;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.myst3ry.financemanager.R;

public enum TabBarScreens {
    MAIN(R.string.tab_main, R.drawable.ic_tab_main),
    FEED(R.string.tab_feed, R.drawable.ic_tab_feed),
    REPORT(R.string.tab_report, R.drawable.ic_tab_report),
    SETTINGS(R.string.tab_settings, R.drawable.ic_tab_settings);

    @StringRes
    private int title;
    @DrawableRes
    private int icon;

    TabBarScreens(int title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
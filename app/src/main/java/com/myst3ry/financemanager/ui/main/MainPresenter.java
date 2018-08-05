package com.myst3ry.financemanager.ui.main;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;

@InjectViewState
public final class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter() {
    }


    public void onTabClicked(int position) {
        Screens screen;
        switch (TabBarScreens.values()[position]) {
            case MAIN:
                screen = Screens.MAIN_SCREEN;
                break;
            //            case FEED:
            //                screen = Screens.FEED_SCREEN;
            //                break;
            case ABOUT:
                screen = Screens.REPORT_SCREEN;
                break;
            case SETTINGS:
                screen = Screens.SETTINGS_SCREEN;
                break;
            default:
                screen = Screens.MAIN_SCREEN;
                break;
        }

        getViewState().activateTab(screen);
    }
}

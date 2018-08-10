package com.myst3ry.financemanager.ui.main;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.ui.main.screens.TabBarScreens;
import com.myst3ry.financemanager.usecase.MainUseCase;

@InjectViewState
public final class MainPresenter extends BasePresenter<MainView> {

    private final MainUseCase useCase;

    public MainPresenter(MainUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        bind(onUi(useCase.executeNecessaryPendingOperation()).subscribe(o -> {
                },
                throwable -> ((Throwable) throwable).printStackTrace()));
    }

    public void onTabClicked(int position, boolean wasSelected) {
        Screens screen;
        switch (TabBarScreens.values()[position]) {
            case MAIN:
                screen = Screens.MAIN_SCREEN;
                break;
            case REPORT:
                screen = Screens.REPORT_SCREEN;
                break;
            case ABOUT:
                screen = Screens.ABOUT_SCREEN;
                break;
            case SETTINGS:
                screen = Screens.SETTINGS_SCREEN;
                break;
            default:
                screen = Screens.MAIN_SCREEN;
                break;
        }

        getViewState().activateTab(screen, wasSelected);
    }

    public void openScreen(Screens screen, Object data, boolean isParent) {
        getViewState().openingScreen(screen, data, isParent);
    }
}

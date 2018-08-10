package com.myst3ry.financemanager.ui.main.screens;

public enum Screens {
    MAIN_SCREEN(TabBarScreens.MAIN),
    ABOUT_SCREEN(TabBarScreens.ABOUT),
    SETTINGS_SCREEN(TabBarScreens.SETTINGS),
    REPORT_SCREEN(TabBarScreens.REPORT),
    BALANCE_SCREEN,
    OPERATION_LIST_SCREEN,
    PERIODIC_SCREEN,
    PATTERNS_SCREEN;


    public final TabBarScreens tab;

    Screens(TabBarScreens tab) {
        this.tab = tab;
    }

    Screens() {
        this(TabBarScreens.MAIN);
    }

    public static boolean contains(String value) {
        return getEnum(value) != null;
    }

    public static Screens getEnum(String value) {
        for (Screens c : Screens.values()) {
            if (c.name().equals(value)) {
                return c;
            }
        }

        return null;
    }
}


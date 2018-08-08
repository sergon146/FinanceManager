package com.myst3ry.financemanager.utils;

import android.view.View;

import com.myst3ry.financemanager.R;

public class ViewUtils {

    public static boolean isTabletUi(View view) {
        return view.getResources().getBoolean(R.bool.is_tablet_ui);
    }
}

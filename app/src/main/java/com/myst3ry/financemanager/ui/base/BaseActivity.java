package com.myst3ry.financemanager.ui.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.main.screens.Screens;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar_title)
    protected TextView title;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        prepareView();
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

    protected void prepareView() {
    }


    public abstract void openScreen(Screens screen, boolean isRoot);

    @SuppressWarnings("unused")
    protected void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    protected void showLongToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void hideScreenTitle() {
        title.setVisibility(View.GONE);
    }
}

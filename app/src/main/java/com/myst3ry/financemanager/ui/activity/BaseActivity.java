package com.myst3ry.financemanager.ui.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.myst3ry.financemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @SuppressWarnings("unused")
    protected void showToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    protected void showLongToast(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

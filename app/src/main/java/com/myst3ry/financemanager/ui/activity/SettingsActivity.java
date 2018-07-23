package com.myst3ry.financemanager.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.fragment.SettingsFragment;

public final class SettingsActivity extends BaseActivity {

    public static Intent newIntent(final Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupActionBar();
        initUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initUI() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_settings, SettingsFragment.newInstance(), SettingsFragment.TAG)
                .disallowAddToBackStack()
                .commit();
    }
}

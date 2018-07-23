package com.myst3ry.financemanager.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.fragment.BalanceFragment;

public final class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.title_main));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(SettingsActivity.newIntent(this));
                return true;
            case R.id.action_about:
                startActivity(AboutActivity.newIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, BalanceFragment.newInstance(), BalanceFragment.TAG)
                .disallowAddToBackStack()
                .commit();
    }
}

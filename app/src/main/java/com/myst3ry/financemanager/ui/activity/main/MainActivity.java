package com.myst3ry.financemanager.ui.activity.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.activity.BaseActivity;
import com.myst3ry.financemanager.ui.activity.about.AboutActivity;
import com.myst3ry.financemanager.ui.fragment.balance.BalanceFragment;
import com.myst3ry.financemanager.ui.fragment.transactions.TransactionCreateFragment;
import com.myst3ry.financemanager.ui.settings.SettingsActivity;

import butterknife.BindView;

public final class MainActivity extends BaseActivity implements BalanceFragment.OnCreateTransactionsListener, MainView {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();

        if (savedInstanceState == null) {
            mPresenter.initAccounts();
            mPresenter.getActualExchangeRates(mDisposables);
        }
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

    private void initPresenter() {
        mPresenter = new MainPresenter(new MainModel(this));
        mPresenter.attachView(this);
    }

    public void showUI() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, BalanceFragment.newInstance(), BalanceFragment.TAG)
                .disallowAddToBackStack()
                .commit();
    }

    @Override
    public void onCreateNewTransaction(final int accountIndex) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, TransactionCreateFragment.newInstance(accountIndex), TransactionCreateFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(TransactionCreateFragment.TAG)
                .commit();
    }

    public void showProgressBar() {
        if (mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void showError(final int code, final String message) {
        showToast(String.format(getString(R.string.http_error), code, message));
    }

    public void showConnectionError() {
        showToast(getString(R.string.connection_error));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDestroyed()) {
            mPresenter.detachView();
        }
    }
}

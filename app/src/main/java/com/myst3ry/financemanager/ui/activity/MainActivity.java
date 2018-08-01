package com.myst3ry.financemanager.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.FinanceManagerApp;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.db.AccountsDbStub;
import com.myst3ry.financemanager.db.RatesStorage;
import com.myst3ry.financemanager.network.CBRApi;
import com.myst3ry.financemanager.network.model.Valute;
import com.myst3ry.financemanager.ui.fragment.BalanceFragment;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public final class MainActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    CBRApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FinanceManagerApp.getNetworkComponent(this).inject(this);
        if (savedInstanceState == null) {
            getActualExchangeRates();
            initUserAccounts();
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

    private void initUserAccounts() {
        final AccountsDbStub stub = AccountsDbStub.getInstance();
        stub.addAccount(Account.newBuilder()
                .setTitle("Наличные")
                .setBalance(new BigDecimal(50000))
                .setCurrencyType(CurrencyType.RUR)
                .setAccountType(AccountType.CASH)
                .build());
        stub.addAccount(Account.newBuilder()
                .setTitle("Кредитная карта")
                .setBalance(new BigDecimal(60000))
                .setCurrencyType(CurrencyType.RUR)
                .setAccountType(AccountType.CREDIT)
                .build());
    }

    private void initUI() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, BalanceFragment.newInstance(), BalanceFragment.TAG)
                .disallowAddToBackStack()
                .commit();
    }

    private void showProgressBar() {
        if (mProgressBar.getVisibility() == View.GONE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void getActualExchangeRates() {
        mDisposables.add(mApi.getActualExchangeRates()
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((s) -> showProgressBar())
                .doAfterTerminate(this::hideProgressBar)
                .doFinally(this::initUI)
                .cache()
                .subscribe(response -> saveActualExchangeRates(response.getValutes()), t -> {
                    if (t instanceof HttpException) {
                        final HttpException http = (HttpException) t;
                        showToast(String.format(getString(R.string.http_error), http.code(), http.getLocalizedMessage()));
                    } else {
                        showToast(getString(R.string.connection_error));
                    }
                })
        );
    }

    public void switchContent(final Fragment fragment, final String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(tag)
                .commit();
    }

    private void saveActualExchangeRates(final Valute valutes) {
        RatesStorage.getInstance().saveUSDRate(this, valutes != null ? valutes.getUSD().getValue() : 0f);
    }
}

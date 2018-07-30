package com.myst3ry.financemanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.FinanceManagerApp;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.db.AccountsDbStub;
import com.myst3ry.financemanager.ui.view.DiagramView;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.financemanager.utils.formatter.rate.DefaultRateFormatter;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment {

    public static final String TAG = BalanceFragment.class.getSimpleName();

    @BindView(R.id.tv_current_account)
    TextView mCurrentAccountTextView;
    @BindView(R.id.tv_rur_balance)
    TextView mMainCurBalanceTextView;
    @BindView(R.id.tv_usd_balance)
    TextView mSecondCurBalanceTextView;
    @BindView(R.id.tv_rate_usd)
    TextView mUSDRateTextView;

    @BindView(R.id.diagram_money)
    DiagramView mDiagramView;

    private BalanceFormatterFactory mFormatterFactory = new BalanceFormatterFactory();
    private AppCompatActivity mActivity;
    private Account mAccount;

    private int mAccountIndex = 2;
    private double mUSDRate;

    public static BalanceFragment newInstance() {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUI(mAccountIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle(getString(R.string.title_balance));
        final ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void initUI(final int accountIndex) {
        mAccount = AccountsDbStub.getInstance().getAccounts().get(accountIndex);
        setExchangeRates();
        setAccountInfo();
    }

    private void setExchangeRates() {
        mUSDRate = (double) ((FinanceManagerApp) mActivity.getApplication()).getSavedUSDRate();
        mUSDRateTextView.setText(new DefaultRateFormatter().formatRate(mUSDRate));
    }

    private void setAccountInfo() {
        final Calculations calcModule = Calculations.getInstance(mAccount, mUSDRate);
        final BigDecimal balanceRUR = calcModule.getBalanceInRur();
        final BigDecimal balanceUSD = calcModule.getBalanceInUsd();

        final List<Account> accounts = AccountsDbStub.getInstance().getAccounts();
        BigDecimal totalBalance = new BigDecimal(0);
        for (final Account account : accounts) {
            if (account.getCurrencyType() == CurrencyType.USD) {
                totalBalance = totalBalance.add(calcModule.convertToRur(account.getBalance()));
            } else if (account.getCurrencyType() == CurrencyType.RUR) {
                totalBalance = totalBalance.add(account.getBalance());
            }

        }

        if (mAccount.getCurrencyType() == CurrencyType.RUR) {
            mMainCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.RUR).formatBalance(balanceRUR));
            mSecondCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.USD).formatBalance(balanceUSD));
        } else {
            mMainCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.USD).formatBalance(balanceUSD));
            mSecondCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.RUR).formatBalance(balanceRUR));
        }

        mDiagramView.update(balanceRUR.longValue(), totalBalance.longValue() - balanceRUR.longValue());
        mCurrentAccountTextView.setText(String.format(getString(R.string.text_current_account), mAccount.getTitle()));
    }

    @OnClick(R.id.fab_add_new_transaction)
    void onFabClick() {
        onNewTransactionRequest();
    }

    private void onNewTransactionRequest() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, TransactionCreateFragment.newInstance(mAccountIndex), TransactionCreateFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(TransactionCreateFragment.TAG)
                .commit();
    }
}

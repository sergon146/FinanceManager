package com.myst3ry.financemanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.FinanceManagerApp;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.financemanager.utils.formatter.rate.DefaultRateFormatter;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment {

    public static final String TAG = BalanceFragment.class.getSimpleName();

    @BindView(R.id.tv_rur_balance)
    TextView mRURBalanceTextView;
    @BindView(R.id.tv_usd_balance)
    TextView mUSDBalanceTextView;
    @BindView(R.id.tv_rate_usd)
    TextView mUSDRateTextView;

    private double mUSDRate;

    private BalanceFormatterFactory mFormatterFactory;
    private Account mAccount;
    private Calculations mCalculations;

    private AppCompatActivity mActivity;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFormatterFactory = new BalanceFormatterFactory();
        initAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setExchangeRates();
        setCurrentBalance();
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

    private void initAccount() {
        mAccount = new Account("Custom", new BigDecimal("12354123"), CurrencyType.RUR, AccountType.CASH);
    }

    private void setExchangeRates() {
        mUSDRate = (double) ((FinanceManagerApp) mActivity.getApplication()).getSavedRates();
        mUSDRateTextView.setText(new DefaultRateFormatter().formatRate(mUSDRate));

        //replace
        mCalculations = Calculations.getInstance(mAccount, mUSDRate);
    }

    //todo get balance from db
    private void setCurrentBalance() {
        mRURBalanceTextView.setText(mFormatterFactory.create(CurrencyType.RUR).formatBalance(mCalculations.getBalanceInRur()));
        mUSDBalanceTextView.setText(mFormatterFactory.create(CurrencyType.USD).formatBalance(mCalculations.getBalanceInUsd()));
    }

    @OnClick(R.id.fab_add_new_transaction)
    void onFabClick() {
        onNewTransactionRequest();
    }

    private void onNewTransactionRequest() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_main, TransactionCreateFragment.newInstance(), TransactionCreateFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(TransactionCreateFragment.TAG)
                .commit();
    }
}

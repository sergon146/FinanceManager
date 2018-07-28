package com.myst3ry.financemanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.financemanager.utils.formatter.rate.DefaultRateFormatter;
import com.myst3ry.financemanager.utils.formatter.rate.RateFormatter;

import java.math.BigDecimal;

import butterknife.BindView;

public final class BalanceFragment extends BaseFragment {

    @BindView(R.id.tv_rur_balance)
    TextView mBalanceRUR;
    @BindView(R.id.tv_usd_balance)
    TextView mBalanceUSD;
    @BindView(R.id.tv_buy_usd)
    TextView mBuyUsdRate;
    @BindView(R.id.tv_sell_usd)
    TextView mSellUsdRate;

    public static final String TAG = BalanceFragment.class.getSimpleName();
    private BalanceFormatterFactory mFormatterFactory;
    private Account mAccount;
    private Calculations mCalculations;

    public static BalanceFragment newInstance() {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFormatterFactory = new BalanceFormatterFactory();
        initAccount();
        initCalculationsModule();
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

    private void initAccount() {
        mAccount = new Account();
        mAccount.setBalance(new BigDecimal("12354123"));
        mAccount.setAccountType(AccountType.CASH);
        mAccount.setCurrencyType(CurrencyType.RUR);
    }

    private void initCalculationsModule() {
        mCalculations = Calculations.getInstance(mAccount, 64.0);
    }

    private void setExchangeRates() {
        final RateFormatter rateFormatter = new DefaultRateFormatter();
        mBuyUsdRate.setText(rateFormatter.formatRate(mCalculations.getRate()));
        mSellUsdRate.setText(rateFormatter.formatRate(mCalculations.getRate()));
    }

    private void setCurrentBalance() {
        mBalanceRUR.setText(mFormatterFactory.create(CurrencyType.RUR).formatBalance(mCalculations.getBalanceInRur()));
        mBalanceUSD.setText(mFormatterFactory.create(CurrencyType.USD).formatBalance(mCalculations.getBalanceInUsd()));
    }
}

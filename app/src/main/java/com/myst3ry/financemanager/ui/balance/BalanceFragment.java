package com.myst3ry.financemanager.ui.balance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.data.local.AccountsDbStub;
import com.myst3ry.financemanager.data.local.RatesStorage;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.financemanager.utils.formatter.rate.DefaultRateFormatter;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment {

    @BindView(R.id.current_account)
    TextView currentAccountTextView;
    @BindView(R.id.rur_balance)
    TextView mainCurBalanceTextView;
    @BindView(R.id.usd_balance)
    TextView secondCurBalanceTextView;
    @BindView(R.id.rate_usd)
    TextView UsdRateTextView;

    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private Account account;
    private int accountIndex;
    private double usdRate;

    public static BalanceFragment newInstance() {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUI(accountIndex);
    }

    private void initUI(final int accountIndex) {
        account = AccountsDbStub.getInstance().getAccounts().get(accountIndex);
        setExchangeRates();
        setAccountInfo();
    }

    private void setExchangeRates() {
        usdRate = (double) RatesStorage.getInstance().getUsdRate(getActivity());
        UsdRateTextView.setText(new DefaultRateFormatter().formatRate(usdRate));
    }

    private void setAccountInfo() {
        final Calculations calcModule = Calculations.getInstance(account, usdRate);
        final BigDecimal balanceRUR = calcModule.getBalanceInRur();
        final BigDecimal balanceUSD = calcModule.getBalanceInUsd();
        final BigDecimal totalBalance = calcModule.getTotalBalance(AccountsDbStub.getInstance().getAccounts());

        mainCurBalanceTextView.setText(formatterFactory.create(CurrencyType.RUR).formatBalance(balanceRUR));
        secondCurBalanceTextView.setText(formatterFactory.create(CurrencyType.USD).formatBalance(balanceUSD));

        currentAccountTextView.setText(String.format(getString(R.string.text_current_account), account.getTitle()));
    }

    @OnClick(R.id.fab_add_new_transaction)
    void onFabClick() {
        activity.openScreen(Screens.TRANSACTIONS_SCREEN, false);
    }

    @Override
    public String getScreenTag() {
        return "BalanceFragment";
    }
}

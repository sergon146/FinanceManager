package com.myst3ry.financemanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.StringUtils;

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

    public static BalanceFragment newInstance() {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setExchangeRates() {
        mBuyUsdRate.setText(StringUtils.formatRate(Calculations.getBuyRate()));
        mSellUsdRate.setText(StringUtils.formatRate(Calculations.getSellRate()));
    }

    private void setCurrentBalance() {
        mBalanceRUR.setText(StringUtils.formatBalance(Calculations.getBalanceInRur().toString(), CurrencyType.RUR));
        mBalanceUSD.setText(StringUtils.formatBalance(Calculations.getBalanceInUsd().toString(), CurrencyType.USD));
    }
}

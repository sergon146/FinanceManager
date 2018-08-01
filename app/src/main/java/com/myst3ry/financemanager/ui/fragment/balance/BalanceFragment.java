package com.myst3ry.financemanager.ui.fragment.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.myst3ry.financemanager.ui.fragment.BaseFragment;
import com.myst3ry.financemanager.ui.view.DiagramView;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.financemanager.utils.formatter.rate.DefaultRateFormatter;

import java.math.BigDecimal;

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
    private OnCreateTransactionsListener mListener;
    private Account mAccount;
    private int mAccountIndex;
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
        if (context instanceof OnCreateTransactionsListener) {
            mListener = (OnCreateTransactionsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnCreateTransactionsListener");
        }
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
        mUSDRate = (double) RatesStorage.getInstance().getUSDRate(getActivity());
        mUSDRateTextView.setText(new DefaultRateFormatter().formatRate(mUSDRate));
    }

    private void setAccountInfo() {
        final Calculations calcModule = Calculations.getInstance(mAccount, mUSDRate);
        final BigDecimal balanceRUR = calcModule.getBalanceInRur();
        final BigDecimal balanceUSD = calcModule.getBalanceInUsd();
        final BigDecimal totalBalance = calcModule.getTotalBalance(AccountsDbStub.getInstance().getAccounts());

        mMainCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.RUR).formatBalance(balanceRUR));
        mSecondCurBalanceTextView.setText(mFormatterFactory.create(CurrencyType.USD).formatBalance(balanceUSD));

        mDiagramView.update(balanceRUR.longValue(), totalBalance.longValue() - balanceRUR.longValue());
        mCurrentAccountTextView.setText(String.format(getString(R.string.text_current_account), mAccount.getTitle()));
    }

    @OnClick(R.id.fab_add_new_transaction)
    void onFabClick() {
        if (mListener != null) {
            mListener.onCreateNewTransaction(mAccountIndex);
        }
    }

    public interface OnCreateTransactionsListener {
        void onCreateNewTransaction(final int index);
    }
}

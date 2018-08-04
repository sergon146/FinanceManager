package com.myst3ry.financemanager.ui.balance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment<BalancePresenter>
        implements BalanceView {
    private static final String UUID_KEY = "UUID_KEY";
    @Inject
    @InjectPresenter
    public BalancePresenter presenter;
    @BindView(R.id.rur_balance)
    TextView mainCurBalanceTextView;
    @BindView(R.id.usd_balance)
    TextView secondCurBalanceTextView;
    @BindView(R.id.transaction_recycler)
    RecyclerView transactionRecycler;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private UUID accountUUid;
    private TransactionAdapter adapter;

    public static BalanceFragment newInstance(UUID accountUuid) {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        args.putSerializable(UUID_KEY, accountUuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @ProvidePresenter
    protected BalancePresenter providePresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            accountUUid = (UUID) getArguments().getSerializable(UUID_KEY);
            getPresenter().setCurrentUuid(accountUUid);
        }
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    protected void prepareViews() {
        adapter = new TransactionAdapter();
        transactionRecycler.setAdapter(adapter);
        transactionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @OnClick(R.id.fab_add)
    void onFabClick() {
        activity.openScreen(Screens.TRANSACTIONS_SCREEN, accountUUid, false);
    }

    @Override
    public void showTotalBalance(Account account) {
        setScreenTitle(account.getTitle());
        mainCurBalanceTextView.setText(formatterFactory.create(account.getCurrencyType())
                .formatBalance(account.getBalance()));
    }

    @Override
    public void showExchangedBalance(BigDecimal amount, CurrencyType type) {
        secondCurBalanceTextView.setText(formatterFactory.create(type).formatBalance(amount));
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        adapter.setTransactions(transactions);
    }

    @Override
    public String getScreenTag() {
        return "BalanceFragment";
    }
}

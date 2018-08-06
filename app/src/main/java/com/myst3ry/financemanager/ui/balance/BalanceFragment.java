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
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.OperationAdapter;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.Balance;
import com.myst3ry.model.Operation;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment<BalancePresenter>
        implements BalanceView {
    private static final String ID_KEY = "ID_KEY";
    @Inject
    @InjectPresenter
    public BalancePresenter presenter;

    @BindView(R.id.main_currency)
    TextView mainCurrency;
    @BindView(R.id.main_balance)
    TextView mainBalance;
    @BindView(R.id.additional_currency)
    TextView additionalCurrency;
    @BindView(R.id.additional_balance)
    TextView additionalBalance;
    @BindView(R.id.operation_recycler)
    RecyclerView operationRecycler;
    @BindView(R.id.empty)
    View emptyHolder;

    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private long accountUUid;
    private OperationAdapter adapter;

    public static BalanceFragment newInstance(long id) {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ID_KEY, id);
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
            accountUUid = (long) getArguments().getSerializable(ID_KEY);
            getPresenter().setCurrentUuid(accountUUid);
        }
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    protected void prepareViews() {
        adapter = new OperationAdapter();
        operationRecycler.setAdapter(adapter);
        operationRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @OnClick(R.id.fab_add)
    void onFabClick() {
        activity.openScreen(Screens.CREATE_OPERATIONS_SCREEN, accountUUid, false);
    }

    @Override
    public void setupTitle(String title) {
        setScreenTitle(title);
    }

    @Override
    public void showMainBalance(Balance balance) {
        mainCurrency.setText(balance.getCurrencyType().name());
        mainBalance.setText(formatterFactory.create(balance.getCurrencyType())
                .formatBalance(balance.getAmount()));
    }

    @Override
    public void showAdditionalBalance(Balance balance) {
        additionalCurrency.setText(balance.getCurrencyType().name());
        additionalBalance.setText(formatterFactory.create(balance.getCurrencyType())
                .formatBalance(balance.getAmount()));
    }


    @OnClick(R.id.periodic_icon)
    void onPeriodicClick() {
        openScreen(Screens.OPERATIONS_LIST_SCREEN, null);
    }

    @Override
    public void showOperations(List<Operation> operations) {
        if (operations.isEmpty()) {
            emptyHolder.setVisibility(View.VISIBLE);
        } else {
            emptyHolder.setVisibility(View.GONE);
            adapter.setOperations(operations);
        }
    }

    @Override
    public String getScreenTag() {
        return "BalanceFragment";
    }
}

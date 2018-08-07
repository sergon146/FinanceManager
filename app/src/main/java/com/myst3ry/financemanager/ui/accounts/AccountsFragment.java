package com.myst3ry.financemanager.ui.accounts;

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
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter;
import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.AccountAdapter;
import com.myst3ry.financemanager.ui.adapters.FeedAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.PatternAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.PeriodicAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.Balance;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AccountsFragment extends BaseFragment<AccountPresenter> implements AccountView {

    @Inject
    @InjectPresenter
    public AccountPresenter presenter;
    @BindView(R.id.account_rv)
    RecyclerView accountRecycler;
    @BindView(R.id.total_balance)
    TextView totalBalance;
    @BindView(R.id.additional_balance)
    TextView additionalBalance;
    @BindView(R.id.empty)
    View emptyHolder;
    private boolean isTabletUi;
    private DiffUtilCompositeAdapter accountAdapter;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();

    public static AccountsFragment newInstance() {
        return new AccountsFragment();
    }

    @Override
    @ProvidePresenter
    protected AccountPresenter providePresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        isTabletUi = getResources().getBoolean(R.bool.is_tablet_ui);
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    protected void prepareViews() {
        hideScreenTitle();

        accountRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        accountAdapter = new DiffUtilCompositeAdapter.Builder()
                .add(new AccountAdapter(account ->
                        openScreen(Screens.BALANCE_SCREEN, account)))
                .add(new FeedAccountAdapterDelegate(() ->
                        openScreen(Screens.FEED_SCREEN, null)))
                .add(new PeriodicAccountAdapterDelegate(() ->
                        openScreen(Screens.PERIODIC_SCREEN, null)))
                .add(new PatternAccountAdapterDelegate(() ->
                        openScreen(Screens.PERIODIC_SCREEN, null)))
                .build();
        accountRecycler.setAdapter(accountAdapter);
    }

    @Override
    public void showAccounts(List<IComparableItem> accounts) {
        if (accounts.isEmpty()) {
            emptyHolder.setVisibility(View.VISIBLE);
        } else {
            emptyHolder.setVisibility(View.GONE);
            accountAdapter.swapData(accounts);
        }

        if (isTabletUi && !accounts.isEmpty()) {
            openScreen(Screens.FEED_SCREEN, null, false);
        }
    }

    @Override
    public void showPrimaryBalance(Balance balance) {
        this.totalBalance.setText(formatterFactory.create(balance.getCurrencyType())
                .formatBalance(balance.getAmount()));
    }

    @Override
    public void showAdditionalBalance(Balance balance) {
        this.additionalBalance.setText(formatterFactory.create(balance.getCurrencyType())
                .formatBalance(balance.getAmount()));
    }

    @Override
    public String getScreenTag() {
        return "AccountsFragment";
    }
}

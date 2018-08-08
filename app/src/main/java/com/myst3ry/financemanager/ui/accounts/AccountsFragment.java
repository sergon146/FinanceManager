package com.myst3ry.financemanager.ui.accounts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter;
import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.adapters.account.AccountAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.account.FeedAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.account.PatternAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.adapters.account.PeriodicAccountAdapterDelegate;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.AccountItemType;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AccountsFragment extends BaseFragment<AccountPresenter> implements AccountView {

    @Inject
    @InjectPresenter
    public AccountPresenter presenter;
    @BindView(R.id.account_rv)
    RecyclerView accountRecycler;
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
                .add(new AccountAdapterDelegate(pos -> getPresenter()
                        .openDetailScreen(pos, pos)))
                .add(new FeedAccountAdapterDelegate(pos -> getPresenter()
                        .openDetailScreen(AccountItemType.FEED, pos)))
                .add(new PeriodicAccountAdapterDelegate(pos -> getPresenter()
                        .openDetailScreen(AccountItemType.PERIODIC, pos)))
                .add(new PatternAccountAdapterDelegate(pos -> getPresenter()
                        .openDetailScreen(AccountItemType.PATTERN, pos)))
                .build();
        accountRecycler.setAdapter(accountAdapter);
    }

    @Override
    public void showAccounts(List<IComparableItem> accounts, int activatePos) {
        accountAdapter.swapData(accounts);
        accountAdapter.notifyDataSetChanged();

        if (isTabletUi && activatePos == 0) {
            openScreen(Screens.OPERATION_LIST_SCREEN, accounts.get(activatePos), false);
        }
    }

    @Override
    public void showEmpty() {
        emptyHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyHolder.setVisibility(View.GONE);
    }

    @Override
    public String getScreenTag() {
        return "AccountsFragment";
    }
}

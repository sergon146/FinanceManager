package com.myst3ry.financemanager.ui.accounts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.accounts.adapter.AccountAdapter;
import com.myst3ry.financemanager.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class AccountFragment extends BaseFragment implements AccountView {

    @BindView(R.id.account_rv)
    RecyclerView accountRecycler;

    private AccountPresenter presenter;
    private AccountAdapter accountAdapter;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    private void initPresenter() {
        presenter = new AccountPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void prepareViews() {
        hideScreenTitle();

        accountRecycler.setHasFixedSize(true);
        accountRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        accountAdapter = new AccountAdapter();
    }

    @Override
    public String getScreenTag() {
        return "AccountFragment";
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        accountAdapter.setAccounts(accounts);
    }
}

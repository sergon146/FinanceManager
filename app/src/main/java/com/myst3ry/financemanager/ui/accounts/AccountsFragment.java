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
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.accounts.adapter.AccountAdapter;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AccountsFragment extends BaseFragment<AccountPresenter> implements AccountView {

    @Inject
    @InjectPresenter
    public AccountPresenter presenter;
    @BindView(R.id.account_rv)
    RecyclerView accountRecycler;
    private AccountAdapter accountAdapter;

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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    protected void prepareViews() {
        hideScreenTitle();

        accountRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        accountAdapter = new AccountAdapter(ac ->
                openScreen(Screens.BALANCE_SCREEN, ac.getUuid()));
        accountRecycler.setAdapter(accountAdapter);
    }

    @Override
    public String getScreenTag() {
        return "AccountsFragment";
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        accountAdapter.setAccounts(accounts);
    }
}
package com.myst3ry.financemanager.data.local;

import com.myst3ry.calculations.model.Account;

import java.util.ArrayList;
import java.util.List;

public final class AccountsDbStub {

    private static volatile AccountsDbStub INSTANCE;
    private List<Account> mAccounts = new ArrayList<>();

    public static AccountsDbStub getInstance() {
        AccountsDbStub instance = INSTANCE;
        if (instance == null) {
            synchronized (AccountsDbStub.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new AccountsDbStub();
                }
            }
        }
        return instance;
    }

    public void addAccount(final Account account) {
        this.mAccounts.add(account);
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }

    public Account getAccount(final int index) {
        return mAccounts.get(index);
    }
}

package com.myst3ry.financemanager.db;

import com.myst3ry.calculations.model.Account;

import java.util.ArrayList;
import java.util.List;

public final class AccountsDbStub {

    private List<Account> mAccounts = new ArrayList<>();

    private static volatile AccountsDbStub INSTANCE;

    public static AccountsDbStub getInstance() {
        AccountsDbStub instance = INSTANCE;
        if (instance == null) {
            synchronized (AccountsDbStub.class) {
                instance = INSTANCE;
            }
            if (instance == null) {
                instance = INSTANCE = new AccountsDbStub();
            }
        }
        return instance;
    }

    public void addAccount(final Account account) {
        this.mAccounts.add(account);
    }

    public void removeAccount(final int accountId) {
        for (final Account account : mAccounts) {
            if (account.getId() == accountId) {
                mAccounts.remove(account);
            }
        }
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }
}

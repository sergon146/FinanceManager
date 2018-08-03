package com.myst3ry.financemanager.repository;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.data.dao.AccountsDao;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;

public class AccountRepository {
    private final AccountsDao accountDb;

    public AccountRepository(AccountsDao accountDb) {
        this.accountDb = accountDb;
    }

    public Observable<List<Account>> getAccounts() {
        return accountDb.getAccounts();
    }

    public Observable<Account> getAccount(UUID uuid) {
        return accountDb.getAccount(uuid);
    }
}

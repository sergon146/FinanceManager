package com.myst3ry.financemanager.repository;

import com.myst3ry.financemanager.data.dao.AccountDao;
import com.myst3ry.model.Account;

import java.util.List;

import io.reactivex.Flowable;

public class AccountRepository extends BaseRepository {
    private final AccountDao accountDao;

    public AccountRepository(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Flowable<List<Account>> getAccounts() {
        return accountDao.getAll();
    }

    public Flowable<Account> getAccount(long id) {
        return accountDao.getById(id);
    }
}

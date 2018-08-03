package com.myst3ry.financemanager.data.dao;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


public final class AccountsDao {

    private List<Account> accounts = new ArrayList<>();
    private Subject<List<Account>> accountSubj = BehaviorSubject.create();

    public AccountsDao() {
        initStubAccounts();
    }

    private void initStubAccounts() {
        addAccount(Account.newBuilder()
                .setTitle("Наличка")
                .setBalance(new BigDecimal(50000.01))
                .setCurrencyType(CurrencyType.RUR)
                .setAccountType(AccountType.CASH)
                .build());

        addAccount(Account.newBuilder()
                .setTitle("Сбербанк")
                .setBalance(new BigDecimal(3023.55))
                .setCurrencyType(CurrencyType.USD)
                .setAccountType(AccountType.CREDIT)
                .build());
    }

    public void addAccount(final Account account) {
        this.accounts.add(account);
        accountSubj.onNext(accounts);
    }

    public Observable<List<Account>> getAccounts() {
        return accountSubj;
    }

    public Account getAccount(final int index) {
        return accounts.get(index);
    }

    public Observable<Account> getAccount(UUID uuid) {
        return accountSubj.flatMap(Observable::fromIterable)
                .filter(account -> account.getUuid().equals(uuid));
    }
}

package com.myst3ry.financemanager.data.dao;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.AccountType;
import com.myst3ry.calculations.model.CurrencyType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;

public final class AccountsDao {

    private List<Account> accounts = new ArrayList<>();

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
                .setAccountType(AccountType.DEBIT_CARD)
                .build());
    }

    public void addAccount(final Account account) {
        this.accounts.add(account);
    }

    public Observable<List<Account>> getAccounts() {
        return Observable.just(accounts);
    }

    public Account getAccount(final int index) {
        return accounts.get(index);
    }

    public Observable<Account> getAccount(UUID uuid) {
        return Observable.just(accounts).flatMap(Observable::fromIterable)
                .filter(account -> account.getUuid().equals(uuid));
    }
}

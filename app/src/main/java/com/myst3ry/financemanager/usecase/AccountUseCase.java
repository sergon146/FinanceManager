package com.myst3ry.financemanager.usecase;


import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.data.remote.model.Valute;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;

import java.util.List;

import io.reactivex.Observable;

public class AccountUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;

    public AccountUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
    }

    public Observable<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }

    public Observable<Valute> getExchangeRate() {
        return exchangeRepository.getExchangeRate();
    }
}

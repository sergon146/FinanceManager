package com.myst3ry.financemanager.usecase;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;

import java.math.BigDecimal;
import java.util.UUID;

import io.reactivex.Observable;

public class BalanceUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;

    public BalanceUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
    }

    public Observable<Account> getAccountBalance(UUID uuid) {
        return accountRepository.getAccount(uuid);
    }

    public Observable<BigDecimal> getExchangeBalance(BigDecimal amount, CurrencyType type) {
        return exchangeRepository.getExchangedAmount(amount, type);
    }

}

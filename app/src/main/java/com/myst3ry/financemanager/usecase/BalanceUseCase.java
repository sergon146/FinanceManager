package com.myst3ry.financemanager.usecase;

import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;

public class BalanceUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;
    private TransactionRepository transactionRepository;

    public BalanceUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
        this.transactionRepository = transactionRepository;
    }

    public Observable<Account> getAccountBalance(UUID uuid) {
        return accountRepository.getAccount(uuid);
    }

    public Observable<BigDecimal> getExchangeBalance(BigDecimal amount, CurrencyType type) {
        return exchangeRepository.getExchangedAmount(amount, type);
    }

    public Observable<List<Transaction>> getTransactions(Account account) {
        return transactionRepository.getTransactions();
    }
}

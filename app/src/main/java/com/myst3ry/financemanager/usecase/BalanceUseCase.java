package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Account;
import com.myst3ry.model.Balance;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.Operation;

import java.util.List;

import io.reactivex.Flowable;

public class BalanceUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;
    private final OperationRepository OperationRepository;

    public BalanceUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository,
                          OperationRepository OperationRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
        this.OperationRepository = OperationRepository;
    }

    public Flowable<Account> getAccountBalance(long id) {
        return accountRepository.getAccount(id);
    }

    public Flowable<Balance> getExchangeBalance(Balance balance) {
        CurrencyType outCurrency = Utils.Currency.getOutCurrency(balance.getCurrencyType());
        return exchangeRepository.getExchangeRate(outCurrency).map(rate ->
                new Balance(outCurrency, balance.getAmount().multiply(rate.getRate())));
    }

    public Flowable<List<Operation>> getOperations(Account account) {
        return OperationRepository.getOperations(account);
    }
}

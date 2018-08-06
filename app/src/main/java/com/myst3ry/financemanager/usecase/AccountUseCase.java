package com.myst3ry.financemanager.usecase;


import android.util.Pair;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Account;
import com.myst3ry.model.Balance;
import com.myst3ry.model.CurrencyType;

import java.util.List;

import io.reactivex.Flowable;

public class AccountUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;

    public AccountUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
    }

    public Flowable<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }

    public Flowable<Pair<Balance, Balance>> getBalanceSum(CurrencyType primaryType,
                                                          CurrencyType additionalType) {
        return Flowable.combineLatest(
                exchangeRepository.getExchangeRate(primaryType),
                exchangeRepository.getExchangeRate(additionalType),
                accountRepository.getAccounts(), (exchangeRate, addRate, accounts) ->
                        Utils.Balances.getBalanceSum(primaryType, exchangeRate, addRate, accounts)
        );
    }
}

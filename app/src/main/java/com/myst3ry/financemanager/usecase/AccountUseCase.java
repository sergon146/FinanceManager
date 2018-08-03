package com.myst3ry.financemanager.usecase;


import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.repository.AccountRepository;

import java.util.List;

import io.reactivex.Observable;

public class AccountUseCase {
    private final AccountRepository accountRepository;

    public AccountUseCase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Observable<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }
}

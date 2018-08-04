package com.myst3ry.financemanager.usecase;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.TransactionRepository;

import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TransactionCreateUseCase {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionCreateUseCase(AccountRepository accountRepository,
                                    TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Observable<Account> getAccount(UUID uuid) {
        return accountRepository.getAccount(uuid);
    }

    public Completable addTransaction(Account account, Transaction transaction) {
        return transactionRepository.addTransaction(transaction);
    }
}

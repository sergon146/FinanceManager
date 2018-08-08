package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class AddOperationUseCase {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public AddOperationUseCase(AccountRepository accountRepository,
                               OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    public Flowable<Account> getAccount(long id) {
        return accountRepository.getAccount(id);
    }

    public Completable addOperation(Operation operation, PeriodicOperation periodic) {
        return operationRepository.addOperation(operation, periodic);
    }

    public Flowable<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }
}

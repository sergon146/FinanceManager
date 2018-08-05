package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class OperationCreateUseCase {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    public OperationCreateUseCase(AccountRepository accountRepository,
                                  OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    public Flowable<Account> getAccount(long id) {
        return accountRepository.getAccount(id);
    }

    public Completable addOperation(Operation operation) {
        return operationRepository.addOperation(operation);
    }
}

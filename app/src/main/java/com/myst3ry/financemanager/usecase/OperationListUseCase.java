package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.repository.TemplateRepository;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;
import com.myst3ry.model.Template;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class OperationListUseCase {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final TemplateRepository templateRepository;

    public OperationListUseCase(OperationRepository operationRepository,
                                AccountRepository accountRepository,
                                TemplateRepository templateRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.templateRepository = templateRepository;
    }

    public Flowable<Account> getAccount(long id) {
        return accountRepository.getAccount(id);
    }

    public Flowable<List<Operation>> getOperations(Long id) {
        return operationRepository.getOperations(id);
    }

    public Flowable<List<PeriodicOperation>> getPeriodicOperations() {
        return operationRepository.getPeriodicOperations();
    }

    public Completable togglePeriodic(boolean isActive, PeriodicOperation periodic) {
        return operationRepository.togglePeriodic(isActive, periodic);
    }

    public Flowable<List<Operation>> getFeed() {
        return Flowable.combineLatest(
                operationRepository.getAllOperations(),
                accountRepository.getAccounts(),
                (operations, accounts) -> {
                    for (Operation operation : operations) {
                        for (Account account : accounts) {
                            if (operation.getAccountId() == account.getId()) {
                                operation.setAccount(account);
                            }
                        }
                    }
                    return operations;
                });
    }

    public Flowable<List<Template>> getTemplates() {
        return templateRepository.getAllTemplate();
    }

    public Completable deletePeriodic(PeriodicOperation periodic) {
        return operationRepository.deletePeriodic(periodic);
    }

    public Completable deleteOperaion(Operation operation) {
        return operationRepository.deleteOperation(operation);
    }
}

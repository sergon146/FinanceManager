package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.repository.TemplateRepository;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;
import com.myst3ry.model.Template;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class AddOperationUseCase {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private TemplateRepository templateRepository;

    public AddOperationUseCase(AccountRepository accountRepository,
                               OperationRepository operationRepository,
                               TemplateRepository templateRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.templateRepository = templateRepository;
    }

    public Completable addOperation(Operation operation,
                                    PeriodicOperation periodic, BigDecimal deltaAmount) {
        return operationRepository.addOperation(operation, periodic, deltaAmount);
    }

    public Flowable<List<Account>> getAccounts() {
        return accountRepository.getAccounts();
    }

    public Completable saveTemplate(Operation operation) {
        return templateRepository.saveTemplate(new Template(operation));
    }

    public Flowable<Operation> loadOperation(long operationId) {
        return operationRepository.getOperation(operationId);
    }
}

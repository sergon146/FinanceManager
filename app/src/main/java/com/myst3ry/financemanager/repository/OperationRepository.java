package com.myst3ry.financemanager.repository;


import com.myst3ry.financemanager.data.dao.OperationDao;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class OperationRepository extends BaseRepository {
    private final OperationDao operationDao;

    public OperationRepository(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    public Flowable<List<Operation>> getOperations(Account account) {
        return operationDao.getByAccount(account.getId());
    }

    public Completable addOperation(Operation operation) {
        int coef = operation.getType().equals(OperationType.EXPENSE) ? -1 : 1;
        return onUi(Completable.fromAction(() ->
                operationDao.addOperationAndUpdateAccountBalance(operation,
                        operation.getAmount().multiply(BigDecimal.valueOf(coef)))));
    }
}

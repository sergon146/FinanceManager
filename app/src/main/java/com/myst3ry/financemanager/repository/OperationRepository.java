package com.myst3ry.financemanager.repository;


import com.myst3ry.financemanager.data.dao.OperationAccountPeriodicDao;
import com.myst3ry.financemanager.data.dao.OperationDao;
import com.myst3ry.financemanager.data.dao.PeriodicDao;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.PeriodicOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class OperationRepository extends BaseRepository {
    private final OperationDao operationDao;
    private final OperationAccountPeriodicDao operationAccountPeriodicDao;
    private final PeriodicDao periodicDao;

    public OperationRepository(OperationDao operationDao,
                               OperationAccountPeriodicDao operationAccountPeriodicDao,
                               PeriodicDao periodicDao) {
        this.operationDao = operationDao;
        this.operationAccountPeriodicDao = operationAccountPeriodicDao;
        this.periodicDao = periodicDao;
    }

    public Flowable<List<Operation>> getOperations(Account account) {
        return getOperations(account.getId());
    }

    public Flowable<List<Operation>> getOperations(Long id) {
        return operationDao.getByAccount(id);
    }

    public Completable addOperation(Operation operation, PeriodicOperation periodic) {
        int coef = operation.getType().equals(OperationType.EXPENSE) ? -1 : 1;

        if (periodic.getDayRepeat() == 0) {
            return flow(Completable.fromAction(() -> operationAccountPeriodicDao.addOperationAndUpdateBalance(operation,
                    operation.getAmount().multiply(BigDecimal.valueOf(coef)))));
        } else {
            return flow(Completable.fromAction(() ->
                    operationAccountPeriodicDao.addOperationPereodicUpdateBalance(operation,
                            operation.getAmount().multiply(BigDecimal.valueOf(coef)), periodic)));
        }
    }

    public Flowable<List<PeriodicOperation>> getPeriodicOperations() {
        return Flowable.combineLatest(
                operationDao.getAll(),
                periodicDao.getAll(),
                (operations, periodicOperation) -> {
                    for (Operation operation : operations) {
                        for (PeriodicOperation periodic : periodicOperation) {
                            if (operation.getId() == periodic.getOperationId()) {
                                periodic.setOperation(operation);
                            }
                        }
                    }
                    return periodicOperation;
                });
    }

    public Flowable<Long> getTotalPeriodicCount() {
        return periodicDao.getTotalCount();
    }

    public Flowable<Long> getActivePeriodicCount() {
        return periodicDao.getActiveCount();
    }

    public Completable togglePeriodic(boolean isTurnOn, PeriodicOperation periodic) {
        return flow(Completable.fromAction(() ->
                periodicDao.togglePeriodic(isTurnOn, periodic.getId())));
    }

    public Flowable executeNecessaryPendingOperation() {
        return getPeriodicOperations().map(periodicOperations -> {
            for (PeriodicOperation periodic : periodicOperations) {
                if (!periodic.isActive()) {
                    continue;
                }

                long today = new Date().getTime();
                long lastExec = periodic.getLastExecution().getTime();
                long dayRepeat = periodic.getDayRepeat();

                long missedExecute = (today - lastExec) / TimeUnit.DAYS.toMillis(dayRepeat);

                Date missDate = periodic.getLastExecution();
                if (missedExecute >= 1) {
                    for (long i = 1; i <= missedExecute; i++) {

                        Operation operation = new Operation(periodic.getOperation());
                        missDate = new Date(lastExec + TimeUnit.DAYS.toMillis(i * dayRepeat));
                        operation.setDate(missDate);
                        operationDao.insert(operation);

                    }
                    periodic.setLastExecution(missDate);
                    periodicDao.updaetLastExecDate(periodic.getId(), periodic.getLastExecution());
                }
            }
            return Flowable.empty();
        });
    }

    public Flowable<Long> getTotalOperations() {
        return operationDao.getTotalCount();
    }

    public Flowable<List<Operation>> getAllOperations() {
        return operationDao.getAll();
    }
}

package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;
import com.myst3ry.model.converter.BigDecimalConverter;

import java.math.BigDecimal;

import io.reactivex.Completable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(BigDecimalConverter.class)
public abstract class OperationAccountPeriodicDao {

    @Insert(onConflict = REPLACE)
    public abstract long insertOperation(Operation operation);

    @Insert(onConflict = REPLACE)
    public abstract long insertPeriodic(PeriodicOperation periodicOperation);

    @Query("UPDATE account SET balance = balance + :amount WHERE id = :accountId")
    public abstract void updateAccountBalance(long accountId, BigDecimal amount);

    @Transaction
    public Completable addOperationPereodicUpdateBalance(Operation operation,
                                                         BigDecimal amount,
                                                         PeriodicOperation periodic) {
        long operationId = insertOperation(operation);
        periodic.setOperationId(operationId);
        updateAccountBalance(operation.getAccountId(), amount);
        insertPeriodic(periodic);
        return Completable.complete();
    }

    @Update
    public abstract void updateOperation(Operation operation);

    @Transaction
    public Completable addOperationAndUpdateBalance(Operation operation, BigDecimal amount) {
        if (operation.getId() == null) {
            insertOperation(operation);
        } else {
            updateOperation(operation);
        }
        updateAccountBalance(operation.getAccountId(), amount);
        return Completable.complete();
    }
}

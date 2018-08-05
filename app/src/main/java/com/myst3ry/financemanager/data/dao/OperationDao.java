package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.Operation;
import com.myst3ry.model.converter.BigDecimalConverter;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
@TypeConverters(BigDecimalConverter.class)
public abstract class OperationDao extends BaseDao<Operation> {
    @Query("SELECT * FROM `operation`")
    public abstract Flowable<List<Operation>> getAll();

    @Query("SELECT * FROM `operation` WHERE accountId = :accId")
    public abstract Flowable<List<Operation>> getByAccount(long accId);

    @Query("UPDATE account SET balance = balance + :amount WHERE id = :accountId")
    public abstract int updateAccountBalance(long accountId, BigDecimal amount);

    @Transaction
    public Completable addOperationAndUpdateAccountBalance(Operation operation,
                                                           BigDecimal amount) {
        insert(operation);
        updateAccountBalance(operation.getAccountId(), amount);
        return Completable.complete();
    }
}

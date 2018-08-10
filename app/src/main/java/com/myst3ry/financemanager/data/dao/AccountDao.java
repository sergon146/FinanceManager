package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.Account;
import com.myst3ry.model.converter.BigDecimalConverter;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Flowable;

@Dao
@TypeConverters(BigDecimalConverter.class)
public abstract class AccountDao extends BaseDao<Account> {
    @Query("SELECT * FROM account")
    public abstract Flowable<List<Account>> getAll();

    @Query("SELECT * FROM account WHERE id = :id")
    public abstract Flowable<Account> getById(long id);

    @Query("UPDATE account SET balance = balance - :amount WHERE id = :accountId")
    public abstract void updateAmount(Long accountId, BigDecimal amount);
}

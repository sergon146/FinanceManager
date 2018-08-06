package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.myst3ry.model.Account;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class AccountDao extends BaseDao<Account> {
    @Query("SELECT * FROM account")
    public abstract Flowable<List<Account>> getAll();

    @Query("SELECT * FROM account WHERE id = :id")
    public abstract Flowable<Account> getById(long id);
}

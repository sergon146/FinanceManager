package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.Operation;
import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.DateConverter;

import java.util.List;

import io.reactivex.Flowable;

@Dao
@TypeConverters( {BigDecimalConverter.class, DateConverter.class})
public abstract class OperationDao extends BaseDao<Operation> {
    @Query("SELECT * FROM `operation` ORDER BY date DESC")
    public abstract Flowable<List<Operation>> getAll();

    @Query("SELECT * FROM `operation` WHERE accountId = :accId ORDER BY date DESC")
    public abstract Flowable<List<Operation>> getByAccount(long accId);

    @Query("SELECT COUNT(id) from `operation` WHERE isActive = 1")
    public abstract Flowable<Long> getTotalCount();
}

package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.Operation;
import com.myst3ry.model.ReportData;
import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.DateConverter;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

@Dao
@TypeConverters( {BigDecimalConverter.class, DateConverter.class})
public abstract class OperationDao extends BaseDao<Operation> {
    @Query("SELECT * FROM `operation` WHERE isActive = 1  ORDER BY date DESC")
    public abstract Flowable<List<Operation>> getActive();

    @Query("SELECT * FROM `operation` ORDER BY date DESC")
    public abstract Flowable<List<Operation>> getAll();

    @Query("SELECT * FROM `operation` WHERE accountId = :accId AND isActive = 1 ORDER BY date DESC")
    public abstract Flowable<List<Operation>> getByAccount(long accId);

    @Query("SELECT COUNT(id) FROM `operation` WHERE isActive = 1")
    public abstract Flowable<Long> getTotalCount();

    @Query("SELECT * FROM operation WHERE id = :id AND isActive = 1 LIMIT 1")
    public abstract Flowable<Operation> getOperation(long id);

    @Query("UPDATE operation SET isActive = 0 WHERE id = :id")
    public abstract long delete(long id);

    @Query("SELECT category, SUM(amount) as sum, type FROM operation " +
            "WHERE isActive = 1 AND date BETWEEN :start AND :end GROUP BY category, type")
    public abstract Flowable<List<ReportData>> getReport(Date start, Date end);
}

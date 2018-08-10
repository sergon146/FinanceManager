package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.PeriodicOperation;
import com.myst3ry.model.converter.DateConverter;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

@Dao
@TypeConverters(DateConverter.class)
public abstract class PeriodicDao extends BaseDao<PeriodicOperation> {

    @Query("SELECT * FROM periodic WHERE isActive = 1 ORDER BY lastExecution DESC")
    public abstract Flowable<List<PeriodicOperation>> getAll();

    @Query("UPDATE periodic SET isTurnOn = :isTurnOn WHERE id = :id AND isActive = 1")
    public abstract long togglePeriodic(boolean isTurnOn, long id);

    @Query("UPDATE periodic SET lastExecution = :newDate WHERE id = :id AND isActive = 1")
    public abstract long updaetLastExecDate(long id, Date newDate);

    @Query("SELECT COUNT(id) FROM periodic WHERE isActive = 1")
    public abstract Flowable<Long> getTotalCount();

    @Query("SELECT COUNT(id) FROM periodic WHERE isTurnOn = 1 AND isActive = 1")
    public abstract Flowable<Long> getActiveCount();

    @Query("UPDATE periodic SET isActive = 0 WHERE id = :id")
    public abstract long delete(long id);
}

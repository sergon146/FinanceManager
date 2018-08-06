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

    @Query("SELECT * from periodic ORDER BY lastExecution DESC")
    public abstract Flowable<List<PeriodicOperation>> getAll();

    @Query("UPDATE periodic SET isActive = :isActive WHERE id = :id")
    public abstract long togglePeriodic(boolean isActive, long id);

    @Query("UPDATE periodic SET lastExecution = :newDate WHERE id = :id")
    public abstract long updaetLastExecDate(long id, Date newDate);


}

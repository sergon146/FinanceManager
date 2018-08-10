package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.model.converter.DateConverter;

import java.util.Date;

@Entity(tableName = "periodic")
public class PeriodicOperation implements IComparableItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long operationId;
    @Ignore
    private Operation operation;
    @TypeConverters(DateConverter.class)
    private Date lastExecution;
    private long dayRepeat;
    private boolean isActive = true;
    private boolean isTurnOn = true;

    public PeriodicOperation(long id,
                             long operationId,
                             Operation operation,
                             Date lastExecution,
                             long dayRepeat,
                             boolean isActive,
                             boolean isTurnOn) {
        this.id = id;
        this.operationId = operationId;
        this.operation = operation;
        this.lastExecution = lastExecution;
        this.dayRepeat = dayRepeat;
        this.isActive = isActive;
        this.isTurnOn = isTurnOn;
    }

    public PeriodicOperation(long dayRepeat) {
        this.dayRepeat = dayRepeat;
        lastExecution = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public long getDayRepeat() {
        return dayRepeat;
    }

    public void setDayRepeat(long dayRepeat) {
        this.dayRepeat = dayRepeat;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object content() {
        return id + operationId + dayRepeat;
    }
}

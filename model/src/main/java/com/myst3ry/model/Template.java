package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.CurrencyTypeConverter;
import com.myst3ry.model.converter.OperationTypeConverter;

import java.math.BigDecimal;

@Entity(tableName = "template")
public class Template implements IComparableItem {
    @PrimaryKey(autoGenerate = true)
    protected Long id;
    protected String title;
    @TypeConverters(OperationTypeConverter.class)
    protected OperationType type;
    @TypeConverters(CurrencyTypeConverter.class)
    protected CurrencyType currencyType;
    @TypeConverters(BigDecimalConverter.class)
    protected BigDecimal amount;
    protected String category;
    protected boolean isActive = true;

    public Template(Long id,
                    String title,
                    OperationType type,
                    CurrencyType currencyType,
                    BigDecimal amount,
                    String category,
                    boolean isActive) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.currencyType = currencyType;
        this.amount = amount;
        this.category = category;
        this.isActive = isActive;
    }

    public Template(Operation operation) {
        this.id = operation.getId();
        this.title = operation.getTitle();
        this.type = operation.getType();
        this.currencyType = operation.getCurrencyType();
        this.amount = operation.getAmount();
        this.category = operation.getCategory();
        this.isActive = operation.isActive();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object content() {
        return id + title + category + amount + currencyType;
    }
}

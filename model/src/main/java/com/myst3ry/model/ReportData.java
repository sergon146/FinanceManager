package com.myst3ry.model;

import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.OperationTypeConverter;

import java.math.BigDecimal;

public class ReportData {
    private String category;
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal sum;
    @TypeConverters(OperationTypeConverter.class)
    private OperationType type;

    public ReportData(String category, BigDecimal sum, OperationType type) {
        this.category = category;
        this.sum = sum;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}

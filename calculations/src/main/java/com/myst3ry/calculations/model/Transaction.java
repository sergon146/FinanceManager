package com.myst3ry.calculations.model;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.OperationType;

import java.math.BigDecimal;

public final class Transaction {

    //todo description
    //todo mark

    private OperationType mOperationType;
    private CurrencyType mCurrencyType;
    private BigDecimal mAmount;

    public Transaction(OperationType operationType, CurrencyType currencyType, BigDecimal amount) {
        this.mOperationType = operationType;
        this.mCurrencyType = currencyType;
        this.mAmount = amount;
    }

    public OperationType getOperationType() {
        return mOperationType;
    }

    public void setOperationType(final OperationType operationType) {
        this.mOperationType = operationType;
    }

    public CurrencyType getCurrencyType() {
        return mCurrencyType;
    }

    public void setCurrencyType(final CurrencyType currencyType) {
        this.mCurrencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return mAmount;
    }

    public void setAmount(final BigDecimal amount) {
        this.mAmount = amount;
    }
}

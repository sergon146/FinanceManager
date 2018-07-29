package com.myst3ry.calculations.model;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;

import java.math.BigDecimal;

public final class Transaction {

    //todo description
    //todo category

    private TransactionType mTransactionType;
    private CurrencyType mCurrencyType;
    private BigDecimal mAmount;

    public Transaction(TransactionType transactionType, CurrencyType currencyType, BigDecimal amount) {
        this.mTransactionType = transactionType;
        this.mCurrencyType = currencyType;
        this.mAmount = amount;
    }

    public TransactionType getOperationType() {
        return mTransactionType;
    }

    public void setOperationType(final TransactionType transactionType) {
        this.mTransactionType = transactionType;
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

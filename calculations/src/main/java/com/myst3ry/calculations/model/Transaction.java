package com.myst3ry.calculations.model;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;

import java.math.BigDecimal;

public final class Transaction {

    private TransactionType mTransactionType;
    private CurrencyType mCurrencyType;
    private BigDecimal mAmount;
    private String mCategory;

    private Transaction(final Builder builder) {
        this.mTransactionType = builder.mTransactionType;
        this.mCurrencyType = builder.mCurrencyType;
        this.mAmount = builder.mAmount;
        this.mCategory = builder.mCategory;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public TransactionType getTransactionType() {
        return mTransactionType;
    }

    public void setTransactionType(final TransactionType transactionType) {
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

    public static final class Builder {

        private TransactionType mTransactionType;
        private CurrencyType mCurrencyType;
        private BigDecimal mAmount;
        private String mCategory;

        private Builder() {
        }

        public Builder setTransactionType(final TransactionType transactionType) {
            this.mTransactionType = transactionType;
            return this;
        }

        public Builder setCurrencyType(final CurrencyType currencyType) {
            this.mCurrencyType = currencyType;
            return this;
        }

        public Builder setAmount(final BigDecimal amount) {
            this.mAmount = amount;
            return this;
        }

        public Builder setCategory(final String category) {
            this.mCategory = category;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}

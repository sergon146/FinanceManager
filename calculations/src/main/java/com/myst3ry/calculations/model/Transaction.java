package com.myst3ry.calculations.model;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;

import java.math.BigDecimal;

public final class Transaction {

    private TransactionType transactionType;
    private CurrencyType currencyType;
    private BigDecimal amount;
    private String category;

    private Transaction(final Builder builder) {
        this.transactionType = builder.transactionType;
        this.currencyType = builder.currencyType;
        this.amount = builder.amount;
        this.category = builder.category;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(final CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public static final class Builder {

        private TransactionType transactionType;
        private CurrencyType currencyType;
        private BigDecimal amount;
        private String category;

        private Builder() {
        }

        public Builder setTransactionType(final TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder setCurrencyType(final CurrencyType currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public Builder setAmount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCategory(final String category) {
            this.category = category;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}

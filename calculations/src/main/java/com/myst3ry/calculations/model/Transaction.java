package com.myst3ry.calculations.model;

import java.math.BigDecimal;

public final class Transaction {

    private TransactionType type;
    private CurrencyType currencyType;
    private BigDecimal amount;
    private String category;

    private Transaction(final Builder builder) {
        this.type = builder.transactionType;
        this.currencyType = builder.currencyType;
        this.amount = builder.amount;
        this.category = builder.category;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(final TransactionType type) {
        this.type = type;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

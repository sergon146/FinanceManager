package com.myst3ry.calculations.model;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;

import java.math.BigDecimal;

public final class Account {

    private String mTitle;
    private BigDecimal mBalance;
    private CurrencyType mCurrencyType;
    private AccountType mAccountType;

    private Account(final Builder builder) {
        mTitle = builder.mTitle;
        mBalance = builder.mBalance;
        mCurrencyType = builder.mCurrencyType;
        mAccountType = builder.mAccountType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getTitle() {
        return mTitle;
    }

    public BigDecimal getBalance() {
        return mBalance;
    }

    public CurrencyType getCurrencyType() {
        return mCurrencyType;
    }

    public AccountType getAccountType() {
        return mAccountType;
    }

    public void setBalance(final BigDecimal balance) {
        this.mBalance = balance;
    }

    public static final class Builder {

        private String mTitle;
        private BigDecimal mBalance;
        private CurrencyType mCurrencyType;
        private AccountType mAccountType;

        private Builder() {
        }

        public Builder setTitle(final String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setBalance(final BigDecimal balance) {
            this.mBalance = balance;
            return this;
        }

        public Builder setCurrencyType(final CurrencyType currencyType) {
            this.mCurrencyType = currencyType;
            return this;
        }

        public Builder setAccountType(final AccountType accountType) {
            this.mAccountType = accountType;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}

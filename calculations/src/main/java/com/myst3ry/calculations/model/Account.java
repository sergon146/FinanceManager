package com.myst3ry.calculations.model;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;

import java.math.BigDecimal;

public final class Account {

    private int mId;
    private String mTitle;
    private BigDecimal mBalance;
    private CurrencyType mCurrencyType;
    private AccountType mAccountType;

    public Account(final int id, final String title, final BigDecimal balance,
                   final CurrencyType currencyType, final AccountType accountType) {
        this.mId = id;
        this.mTitle = title;
        this.mBalance = balance;
        this.mCurrencyType = currencyType;
        this.mAccountType = accountType;
    }

    public int getId() {
        return mId;
    }

    public void setId(final int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        this.mTitle = title;
    }

    public BigDecimal getBalance() {
        return mBalance;
    }

    public void setBalance(final BigDecimal balance) {
        this.mBalance = balance;
    }

    public CurrencyType getCurrencyType() {
        return mCurrencyType;
    }

    public void setCurrencyType(final CurrencyType currencyType) {
        this.mCurrencyType = currencyType;
    }

    public AccountType getAccountType() {
        return mAccountType;
    }

    public void setAccountType(final AccountType accountType) {
        this.mAccountType = accountType;
    }
}

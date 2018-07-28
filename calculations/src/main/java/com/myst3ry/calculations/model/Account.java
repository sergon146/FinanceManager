package com.myst3ry.calculations.model;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;

import java.math.BigDecimal;

public final class Account {

    //todo description
    //todo title

    private BigDecimal mBalance;
    private CurrencyType mCurrencyType;
    private AccountType mAccountType;

    //todo singleton

    public Account(BigDecimal mBalance, CurrencyType mCurrencyType, AccountType mAccountType) {
        this.mBalance = mBalance;
        this.mCurrencyType = mCurrencyType;
        this.mAccountType = mAccountType;
    }

    public Account() {

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

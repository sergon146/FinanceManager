package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.myst3ry.model.converter.AccountTypeConverter;
import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.CurrencyTypeConverter;

import java.math.BigDecimal;

@Entity(tableName = "account")
public class Account extends AccountBaseItem {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal balance;
    @TypeConverters(CurrencyTypeConverter.class)
    private CurrencyType currencyType;
    @TypeConverters(AccountTypeConverter.class)
    private AccountType accountType;

    public Account(long id,
                   String title,
                   BigDecimal balance,
                   CurrencyType currencyType,
                   AccountType accountType) {
        this.id = id;
        this.title = title;
        this.balance = balance;
        this.currencyType = currencyType;
        this.accountType = accountType;
    }

    public Account(final Builder builder) {
        title = builder.title;
        balance = builder.balance;
        currencyType = builder.currencyType;
        accountType = builder.accountType;
    }

    @Ignore
    public Account(AccountType accountType) {
        this.accountType = accountType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public Object id() {
        return id;
    }

    @Override
    public Object content() {
        return title + balance + accountType;
    }

    public static final class Builder {

        private String title;
        private BigDecimal balance;
        private CurrencyType currencyType;
        private AccountType accountType;

        private Builder() {
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setBalance(final BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder setCurrencyType(final CurrencyType currencyType) {
            this.currencyType = currencyType;
            return this;
        }

        public Builder setAccountType(final AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Account build() {
            return new Account(this);
        }
    }
}

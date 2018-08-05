package com.myst3ry.model;

import java.math.BigDecimal;

public class Balance {
    private BigDecimal amount;
    private CurrencyType currencyType;

    public Balance(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Balance(CurrencyType currencyType, BigDecimal amount) {
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }
}

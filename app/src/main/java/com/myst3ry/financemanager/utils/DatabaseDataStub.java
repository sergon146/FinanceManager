package com.myst3ry.financemanager.utils;

import com.myst3ry.model.Account;
import com.myst3ry.model.AccountType;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.Date;

public class DatabaseDataStub {
    private static final String RUB_USD_JSON = "{\"RUB_USD\":0.015787}";
    private static final String USD_RUB_JSON = "{\"USD_RUB\":63.342038}";

    public static Account getRurAccount() {
        return Account.newBuilder()
                .setTitle("Наличные")
                .setBalance(BigDecimal.ZERO)
                .setCurrencyType(CurrencyType.RUB)
                .setAccountType(AccountType.CASH)
                .build();
    }

    public static Account getUsdAccount() {
        return Account.newBuilder()
                .setTitle("Банковская карта")
                .setBalance(BigDecimal.ZERO)
                .setCurrencyType(CurrencyType.USD)
                .setAccountType(AccountType.DEBIT_CARD)
                .build();
    }

    public static ExchangeRate getRubUsdRate() {
        ExchangeRate rate = new ExchangeRate(CurrencyType.RUB.name(),
                CurrencyType.USD.name(), RUB_USD_JSON);
        rate.setDate(new Date(0));
        return rate;
    }

    public static ExchangeRate getUsdRubRate() {
        ExchangeRate rate = new ExchangeRate(CurrencyType.USD.name(),
                CurrencyType.RUB.name(), USD_RUB_JSON);
        rate.setDate(new Date(0));
        return rate;
    }
}

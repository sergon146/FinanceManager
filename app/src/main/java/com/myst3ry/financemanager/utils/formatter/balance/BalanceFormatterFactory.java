package com.myst3ry.financemanager.utils.formatter.balance;

import com.myst3ry.calculations.model.CurrencyType;

public final class BalanceFormatterFactory {

    public BalanceFormatter create(final CurrencyType currency) {
        switch (currency) {
            case RUR:
                return new RurBalanceFormatter();
            case USD:
                return new UsdBalanceFormatter();
            default:
                return new DefaultBalanceFormatter();
        }
    }
}

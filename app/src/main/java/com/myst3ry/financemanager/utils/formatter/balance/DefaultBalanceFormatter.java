package com.myst3ry.financemanager.utils.formatter.balance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

public final class DefaultBalanceFormatter implements BalanceFormatter {

    private static final String DEFAULT_FORMAT_PATTERN = "#,##0.00";

    @Override
    public String formatBalance(final BigDecimal balance) {
        final DecimalFormat decimalFormat = new DecimalFormat(DEFAULT_FORMAT_PATTERN);
        decimalFormat.setCurrency(Currency.getInstance(Locale.getDefault()));
        return decimalFormat.format(balance);
    }
}

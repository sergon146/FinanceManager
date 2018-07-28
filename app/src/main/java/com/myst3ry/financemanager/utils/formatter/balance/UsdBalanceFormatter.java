package com.myst3ry.financemanager.utils.formatter.balance;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class UsdBalanceFormatter implements BalanceFormatter {

    private static final String USD_FORMAT_PATTERN = "#,##0.00 $";

    @Override
    public String formatBalance(final BigDecimal balance) {
        final DecimalFormat decimalFormat = new DecimalFormat(USD_FORMAT_PATTERN);
        return decimalFormat.format(balance);
    }
}

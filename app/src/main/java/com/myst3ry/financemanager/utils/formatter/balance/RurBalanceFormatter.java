package com.myst3ry.financemanager.utils.formatter.balance;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class RurBalanceFormatter implements BalanceFormatter {

    private static final String RUR_FORMAT_PATTERN = "#,##0.00 \u20BD";

    @Override
    public String formatBalance(final BigDecimal balance) {
        final DecimalFormat decimalFormat = new DecimalFormat(RUR_FORMAT_PATTERN);
        return decimalFormat.format(balance);
    }
}

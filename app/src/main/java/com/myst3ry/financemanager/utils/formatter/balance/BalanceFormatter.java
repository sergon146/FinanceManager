package com.myst3ry.financemanager.utils.formatter.balance;

import java.math.BigDecimal;

public interface BalanceFormatter {

    String formatBalance(final BigDecimal balance);
}

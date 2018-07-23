package com.myst3ry.financemanager.utils;

import com.myst3ry.calculations.CurrencyType;

import java.util.Locale;

public final class StringUtils {

    public static String formatRate(final Double rate) {
        return String.format(Locale.getDefault(), "%.2f", rate);
    }

    public static String formatBalance(final String balance, final CurrencyType currency) {
        if (currency == CurrencyType.RUR) {
            return String.format(Locale.getDefault(), "%s \u20BD", balance);
        } else if (currency == CurrencyType.USD) {
            return String.format(Locale.getDefault(), "%s $", balance);
        } else {
            return "";
        }
    }

    private StringUtils() {
    }
}

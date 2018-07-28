package com.myst3ry.financemanager.utils.formatter.rate;

import java.util.Locale;

public final class DefaultRateFormatter implements RateFormatter {

    @Override
    public String formatRate(final Double rate) {
        return String.format(Locale.getDefault(), "%.2f", rate);
    }
}

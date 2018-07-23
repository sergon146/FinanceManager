package com.myst3ry.calculations;

import com.myst3ry.calculations.model.Transaction;

import java.math.BigDecimal;

public final class Calculations {

    private static final int SCALE_TYPE = BigDecimal.ROUND_DOWN;
    private static final int SCALE_VALUE = 2;

    //rur is default
    private static BigDecimal mAccountBalance = new BigDecimal("1562636.22");

    private static Double mBuyRate = 64.00;
    private static Double mSellRate = 63.00;


    public static Double getBuyRate() {
        return mBuyRate;
    }

    public static Double getSellRate() {
        return mSellRate;
    }

    public static BigDecimal getBalanceInRur() {
        return mAccountBalance.setScale(SCALE_VALUE, SCALE_TYPE);
    }

    public static BigDecimal getBalanceInUsd() {
        return convertToUsd(mAccountBalance, getBuyRate());
    }

    private static void income(final Transaction transaction) {
        if (transaction.getOperationType() == OperationType.INCOME) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccountBalance = mAccountBalance.add(transaction.getAmount());
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccountBalance = mAccountBalance.add(convertToRur(transaction.getAmount(), getSellRate()));
            }
        }
    }

    private static void expense(final Transaction transaction) {
        if (transaction.getOperationType() == OperationType.EXPENSE) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccountBalance = mAccountBalance.subtract(transaction.getAmount());
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccountBalance = mAccountBalance.subtract(convertToRur(transaction.getAmount(), getSellRate()));
            }
        }
    }

    private static BigDecimal convertToRur(final BigDecimal amount, final Double rate) {
        return amount.multiply(new BigDecimal(rate));
    }

    private static BigDecimal convertToUsd(final BigDecimal amount, final Double rate) {
        return amount.divide(new BigDecimal(rate), SCALE_VALUE, SCALE_TYPE);
    }

    private Calculations() {
    }
}

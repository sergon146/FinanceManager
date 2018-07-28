package com.myst3ry.calculations;

import com.myst3ry.calculations.model.Transaction;

import java.math.BigDecimal;

public final class Calculations {

    private static final int SCALE_TYPE = BigDecimal.ROUND_DOWN;
    private static final int SCALE_VALUE = 2;

    private Double mBuyRate = 64.00;
    private Double mSellRate = 63.00;

    //rur is default
    private BigDecimal mAccountBalance = new BigDecimal("15626323");

    private static volatile Calculations INSTANCE;

    public static Calculations getInstance() {
        Calculations instance = INSTANCE;
        if (instance == null) {
            synchronized (Calculations.class) {
                instance = INSTANCE;
            }
            if (instance == null) {
                instance = INSTANCE = new Calculations();
            }
        }
        return instance;
    }

    public void income(final Transaction transaction) {
        if (transaction.getOperationType() == OperationType.INCOME) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccountBalance = mAccountBalance.add(transaction.getAmount());
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccountBalance = mAccountBalance.add(convertToRur(transaction.getAmount(), getSellRate()));
            }
        }
    }

    public void expense(final Transaction transaction) {
        if (transaction.getOperationType() == OperationType.EXPENSE) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccountBalance = mAccountBalance.subtract(transaction.getAmount());
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccountBalance = mAccountBalance.subtract(convertToRur(transaction.getAmount(), getSellRate()));
            }
        }
    }

    public BigDecimal getBalanceInRur() {
        return mAccountBalance.setScale(SCALE_VALUE, SCALE_TYPE);
    }

    public BigDecimal getBalanceInUsd() {
        return convertToUsd(mAccountBalance, getBuyRate());
    }

    public Double getBuyRate() {
        return mBuyRate;
    }

    public Double getSellRate() {
        return mSellRate;
    }

    private BigDecimal convertToRur(final BigDecimal amount, final Double rate) {
        return amount.multiply(new BigDecimal(rate));
    }

    private BigDecimal convertToUsd(final BigDecimal amount, final Double rate) {
        return amount.divide(new BigDecimal(rate), SCALE_VALUE, SCALE_TYPE);
    }
}

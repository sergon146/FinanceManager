package com.myst3ry.calculations;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;

import java.math.BigDecimal;

public final class Calculations {

    private static final int SCALE_TYPE = BigDecimal.ROUND_DOWN;
    private static final int SCALE_VALUE = 2;

    private Account mAccount;
    private Double mRate;

    private static volatile Calculations INSTANCE;

    public static Calculations getInstance(final Account account, final Double rate) {
        Calculations instance = INSTANCE;
        if (instance == null) {
            synchronized (Calculations.class) {
                instance = INSTANCE;
            }
            if (instance == null) {
                instance = INSTANCE = new Calculations(account, rate);
            }
        }
        return instance;
    }

    private Calculations(final Account account, final Double rate) { //todo get rate from api
        this.mAccount = account;
        this.mRate = rate;
    }

    public void income(final Transaction transaction) {
        if (transaction.getOperationType() == TransactionType.INCOME) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccount.setBalance(mAccount.getBalance().add(transaction.getAmount()));
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccount.setBalance(mAccount.getBalance().add(convertToRur(transaction.getAmount(), getRate())));
            }
        }
    }

    public void expense(final Transaction transaction) {
        if (transaction.getOperationType() == TransactionType.EXPENSE) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccount.setBalance(mAccount.getBalance().subtract(transaction.getAmount()));
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccount.setBalance(mAccount.getBalance().subtract(convertToRur(transaction.getAmount(), getRate())));
            }
        }
    }

    //todo temp
    public BigDecimal getBalanceInRur() {
        return mAccount.getBalance().setScale(SCALE_VALUE, SCALE_TYPE);
    }

    //todo temp
    public BigDecimal getBalanceInUsd() {
        return convertToUsd(mAccount.getBalance(), getRate());
    }

    public Double getRate() {
        return mRate;
    }

    private BigDecimal convertToRur(final BigDecimal amount, final Double rate) {
        return amount.multiply(new BigDecimal(rate));
    }

    private BigDecimal convertToUsd(final BigDecimal amount, final Double rate) {
        return amount.divide(new BigDecimal(rate), SCALE_VALUE, SCALE_TYPE);
    }
}

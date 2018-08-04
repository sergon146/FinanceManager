package com.myst3ry.calculations;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.calculations.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public final class Calculations {

    private static final int SCALE_TYPE = BigDecimal.ROUND_DOWN;
    private static final int SCALE_VALUE = 2;

    private Account mAccount;
    private double mRate;

    private static volatile Calculations INSTANCE;

    public static Calculations getInstance(final Account account, final double rate) {
        Calculations instance = INSTANCE;
        if (instance == null) {
            synchronized (Calculations.class) {
                instance = INSTANCE;
                if (instance == null) {
                    instance = INSTANCE = new Calculations(account, rate);
                }
            }
        }
        return instance;
    }

    private Calculations(final Account account, final Double rate) {
        this.mAccount = account;
        this.mRate = rate;
    }

    public void income(final Transaction transaction) {
        if (transaction.getType() == TransactionType.INCOME) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccount.setBalance(mAccount.getBalance().add(transaction.getAmount()));
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccount.setBalance(mAccount.getBalance().add(convertToRur(transaction.getAmount())));
            }
        }
    }

    public void expense(final Transaction transaction) {
        if (transaction.getType() == TransactionType.EXPENSE) {
            if (transaction.getCurrencyType() == CurrencyType.RUR) {
                mAccount.setBalance(mAccount.getBalance().subtract(transaction.getAmount()));
            } else if (transaction.getCurrencyType() == CurrencyType.USD) {
                mAccount.setBalance(mAccount.getBalance().subtract(convertToRur(transaction.getAmount())));
            }
        }
    }

    public BigDecimal getTotalBalance(final List<Account> accounts) {
        BigDecimal totalBalance = BigDecimal.ZERO;
        for (final Account account : accounts) {
            if (account.getCurrencyType() == CurrencyType.USD) {
                totalBalance = totalBalance.add(convertToRur(account.getBalance()));
            } else if (account.getCurrencyType() == CurrencyType.RUR) {
                totalBalance = totalBalance.add(account.getBalance());
            }
        }
        return totalBalance;
    }

    public BigDecimal getBalanceInRur() {
        return mAccount.getBalance().setScale(SCALE_VALUE, SCALE_TYPE);
    }

    public BigDecimal getBalanceInUsd() {
        return convertToUsd(mAccount.getBalance());
    }

    public BigDecimal convertToRur(final BigDecimal amount) {
        return amount.multiply(new BigDecimal(mRate));
    }

    public BigDecimal convertToUsd(final BigDecimal amount) {
        return mRate != 0d ? amount.divide(new BigDecimal(mRate), SCALE_VALUE, SCALE_TYPE) : new BigDecimal(0);
    }
}

package com.myst3ry.financemanager.utils;

import android.content.Context;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;
import com.myst3ry.financemanager.R;

import java.util.ArrayList;

public final class Utils {

    public static ArrayList<String> getCurrencyTitles(final Context context) {
        final ArrayList<String> currencies = new ArrayList<>();
        for (final CurrencyType currency : CurrencyType.values()) {
            currencies.add(getCurrencyTitle(currency, context));
        }
        return currencies;
    }

    public static ArrayList<String> getTransactionTitles(final Context context) {
        final ArrayList<String> transactions = new ArrayList<>();
        for (final TransactionType transaction : TransactionType.values()) {
            transactions.add(getTransactionTitle(transaction, context));
        }
        return transactions;
    }

    public static CurrencyType getCurrencyTypeByResId(final int resId) {
        switch (resId) {
            case R.string.dialog_title_rur:
                return CurrencyType.RUR;
            case R.string.dialog_title_usd:
                return CurrencyType.USD;
            default:
                return null;
        }
    }

    public static TransactionType getTransactionTypeByResId(final int resId) {
        switch (resId) {
            case R.string.dialog_title_expense:
                return TransactionType.EXPENSE;
            case R.string.dialog_title_income:
                return TransactionType.INCOME;
            default:
                return null;
        }
    }

    private static String getCurrencyTitle(final CurrencyType currency, final Context context) {
        switch (currency) {
            case RUR:
                return context.getString(R.string.dialog_title_rur);
            case USD:
                return context.getString(R.string.dialog_title_usd);
            default:
                return "";
        }
    }

    private static String getTransactionTitle(final TransactionType transaction, final Context context) {
        switch (transaction) {
            case EXPENSE:
                return context.getString(R.string.dialog_title_expense);
            case INCOME:
                return context.getString(R.string.dialog_title_income);
            default:
                return "";
        }
    }
}

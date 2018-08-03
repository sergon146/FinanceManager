package com.myst3ry.financemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.myst3ry.calculations.AccountType;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;
import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class Utils {

    public static ArrayList<String> getCurrencyTitles(final Context context) {
        final ArrayList<String> currencies = new ArrayList<>();
        for (final CurrencyType currency : CurrencyType.values()) {
            currencies.add(Currency.getSymbol(context, currency));
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

    public static int getCurrencyTypeTitleRes(CurrencyType type) {
        switch (type) {
            case RUR:
                return R.string.dialog_title_rur;
            case USD:
                return R.string.dialog_title_usd;
            default:
                throw new RuntimeException("Unknown type");
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

    public static String getAccountTypeTitle(final Context context, final AccountType type) {
        int resId;
        switch (type) {
            case CASH:
                resId = R.string.cash_type;
                break;
            case CREDIT:
                resId = R.string.card_type;
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
        return context.getString(resId);
    }

    public static Intent createContactIntent(final String mailSubject,
                                             final String mailBody,
                                             final String mailAddress) {
        final String subject = String.format(mailSubject, BuildConfig.VERSION_NAME);
        final String body = String.format(mailBody, Build.MANUFACTURER,
                Build.MODEL, Build.DEVICE, Build.ID, Build.VERSION.RELEASE, Build.VERSION.SDK_INT);

        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        final String uriString = "mailto:" + Uri.encode(mailAddress) +
                "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body);

        intent.setData(Uri.parse(uriString));
        return intent;
    }

    public static class Currency {

        public static String getAmountTitle(final BigDecimal amount, final CurrencyType type) {
            return new BalanceFormatterFactory().create(type).formatBalance(amount);
        }


        private static String getSymbol(final Context context, final CurrencyType currency) {
            int resId;
            switch (currency) {
                case RUR:
                    resId = R.string.rub_symbol;
                    break;
                case USD:
                    resId = R.string.usd_symbol;
                    break;
                default:
                    throw new RuntimeException("Unknown type");
            }

            return context.getString(resId);
        }
    }

}

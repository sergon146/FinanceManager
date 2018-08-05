package com.myst3ry.financemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;

import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountType;
import com.myst3ry.model.Balance;
import com.myst3ry.model.CategoryType;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.ExchangeRate;
import com.myst3ry.model.OperationType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    public static ArrayList<String> getCurrencyTitles(final Context context) {
        final ArrayList<String> currencies = new ArrayList<>();
        for (final CurrencyType currency: CurrencyType.values()) {
            currencies.add(Currency.getSymbol(context, currency));
        }
        return currencies;
    }

    public static ArrayList<String> getOperationTitles(final Context context) {
        final ArrayList<String> operations = new ArrayList<>();
        for (final OperationType operation: OperationType.values()) {
            operations.add(getOperationTitle(operation, context));
        }
        return operations;
    }

    public static CurrencyType getCurrencyTypeByResId(final int resId) {
        switch (resId) {
            case R.string.dialog_title_rub:
                return CurrencyType.RUB;
            case R.string.dialog_title_usd:
                return CurrencyType.USD;
            default:
                return null;
        }
    }

    public static int getCurrencyTypeTitleRes(CurrencyType type) {
        switch (type) {
            case RUB:
                return R.string.dialog_title_rub;
            case USD:
                return R.string.dialog_title_usd;
            default:
                throw new RuntimeException("Unknown type");
        }
    }

    public static OperationType getOperationTypeByResId(final int resId) {
        switch (resId) {
            case R.string.expense:
                return OperationType.EXPENSE;
            case R.string.income:
                return OperationType.INCOME;
            default:
                return null;
        }
    }

    private static String getOperationTitle(final OperationType operation, final Context context) {
        switch (operation) {
            case EXPENSE:
                return context.getString(R.string.expense);
            case INCOME:
                return context.getString(R.string.income);
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
            case DEBIT_CARD:
                resId = R.string.card_type;
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
        return context.getString(resId);
    }

    public static String getOperationTypeTitle(final Context context, final OperationType type) {
        int resId;
        switch (type) {
            case INCOME:
                resId = R.string.income;
                break;
            case EXPENSE:
                resId = R.string.expense;
                break;
            default:
                throw new RuntimeException("Unknown type");
        }
        return context.getString(resId);
    }

    public static String getCategoryTypeTitle(final Context context, final CategoryType type) {
        int resId;

        switch (type) {
            case FOOD:
                resId = R.string.food;
                break;
            case CLOTHES:
                resId = R.string.clothes;
                break;
            case COMMUNAL_PAYMENTS:
                resId = R.string.communal_payments;
                break;
            case REST:
                resId = R.string.rest;
                break;
            case EDUCATION:
                resId = R.string.education;
                break;
            case HOME:
                resId = R.string.home;
                break;
            case FAMILY:
                resId = R.string.family;
                break;
            case AUTO:
                resId = R.string.auto;
                break;
            case TREATMENT:
                resId = R.string.treatment;
                break;
            case SALARY:
                resId = R.string.salary;
                break;
            case OTHER:
                resId = R.string.other;
                break;
            default:
                throw new RuntimeException("Unknown category");
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
                case RUB:
                    resId = R.string.text_currency_rub;
                    break;
                case USD:
                    resId = R.string.text_currency_usd;
                    break;
                default:
                    throw new RuntimeException("Unknown type");
            }

            return context.getString(resId);
        }

        public static CurrencyType getOutCurrency(CurrencyType in) {
            CurrencyType out;
            switch (in) {
                case RUB:
                    out = CurrencyType.USD;
                    break;
                case USD:
                    out = CurrencyType.RUB;
                    break;
                default:
                    out = in;
            }
            return out;
        }

    }

    public static class Balances {
        public static Pair<Balance, Balance> getBalanceSum(final CurrencyType currentCurrency,
                                                           final ExchangeRate primaryRate,
                                                           final ExchangeRate additionalRate,
                                                           final List<Account> accounts) {
            Balance balance = new Balance(currentCurrency);

            BigDecimal sum = BigDecimal.ZERO;
            for (Account account: accounts) {
                if (account.getCurrencyType().equals(currentCurrency)) {
                    sum = sum.add(account.getBalance());
                } else {
                    sum = sum.add(account.getBalance().multiply(primaryRate.getRate()));
                }
            }

            balance.setAmount(sum);

            BigDecimal amount = sum.multiply(additionalRate.getRate());
            Balance additionalBalance = new Balance(CurrencyType.USD, amount);
            return new Pair<>(balance, additionalBalance);
        }
    }
}

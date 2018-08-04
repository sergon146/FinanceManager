package com.myst3ry.financemanager.ui.balance;

import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.ui.base.BaseView;

import java.math.BigDecimal;
import java.util.List;

public interface BalanceView extends BaseView {
    void showTotalBalance(Account account);

    void showExchangedBalance(BigDecimal amount, CurrencyType type);

    void showTransactions(List<Transaction> transactions);
}

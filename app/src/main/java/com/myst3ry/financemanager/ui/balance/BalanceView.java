package com.myst3ry.financemanager.ui.balance;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.ui.base.BaseView;

import java.math.BigDecimal;

public interface BalanceView extends BaseView {
    void showTotalBalance(Account account);

    void showExchangedBalance(BigDecimal amount, CurrencyType type);

}

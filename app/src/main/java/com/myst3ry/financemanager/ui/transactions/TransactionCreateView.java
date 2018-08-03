package com.myst3ry.financemanager.ui.transactions;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.ui.base.BaseView;

public interface TransactionCreateView extends BaseView {
    void showAccountData(Account account);

    void successPerform();
}

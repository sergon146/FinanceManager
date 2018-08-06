package com.myst3ry.financemanager.ui.accounts;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Account;
import com.myst3ry.model.Balance;

import java.util.List;

public interface AccountView extends BaseView {
    void showAccounts(List<Account> accounts);

    void showPrimaryBalance(Balance balance);

    void showAdditionalBalance(Balance balance);
}

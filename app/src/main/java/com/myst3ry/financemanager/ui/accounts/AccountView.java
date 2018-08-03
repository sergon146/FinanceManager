package com.myst3ry.financemanager.ui.accounts;

import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.ui.base.BaseView;

import java.util.List;

public interface AccountView extends BaseView {
    void showAccounts(List<Account> accounts);
}

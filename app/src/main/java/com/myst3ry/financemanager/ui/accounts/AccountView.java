package com.myst3ry.financemanager.ui.accounts;

import com.myst3ry.calculations.model.Account;

import java.util.List;

public interface AccountView {
    void showAccounts(List<Account> accounts);
}

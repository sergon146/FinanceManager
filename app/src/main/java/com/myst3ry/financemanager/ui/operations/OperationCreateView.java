package com.myst3ry.financemanager.ui.operations;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Account;

public interface OperationCreateView extends BaseView {
    void showAccountData(Account account);

    void successPerform();
}

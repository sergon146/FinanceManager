package com.myst3ry.financemanager.ui.dialogs.addoperation;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;

import java.util.List;

public interface AddOperationView extends BaseView {

    void showAccountData(Account account);

    void successPerform();

    void showAccounts(List<Account> accounts);

    void showOperation(Operation operation);

    void dismiss();
}

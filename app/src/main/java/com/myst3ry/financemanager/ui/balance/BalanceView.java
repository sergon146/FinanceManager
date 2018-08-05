package com.myst3ry.financemanager.ui.balance;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Balance;
import com.myst3ry.model.Operation;

import java.util.List;

public interface BalanceView extends BaseView {
    void showMainBalance(Balance balance);

    void showAdditionalBalance(Balance balance);

    void showOperations(List<Operation> Operations);

    void setupTitle(String title);
}

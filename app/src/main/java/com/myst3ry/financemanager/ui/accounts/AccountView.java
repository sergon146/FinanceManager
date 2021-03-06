package com.myst3ry.financemanager.ui.accounts;

import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.ui.base.BaseView;

import java.util.List;

public interface AccountView extends BaseView {
    void showAccounts(List<IComparableItem> accounts, int lastActivePosition);

    void showEmpty();

    void hideEmpty();
}

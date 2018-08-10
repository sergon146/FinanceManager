package com.myst3ry.financemanager.ui.operationslist;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

public interface OperationListView extends BaseView {
    void showOperations(List<IComparableItem> periodicOperation);

    @StateStrategyType(SkipStrategy.class)
    void periodicToggleError(boolean isActive, PeriodicOperation periodic);

    void showEmpty();

    void hideEmpty();

    void showEditDialog(Operation operation);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showAddDialog();
}

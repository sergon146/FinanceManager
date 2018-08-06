package com.myst3ry.financemanager.ui.operationslist;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

public interface OperationListView extends BaseView {
    void showPeriodicOperations(List<PeriodicOperation> periodicOperation);

    @StateStrategyType(SkipStrategy.class)
    void periodicToggleError(boolean isActive, PeriodicOperation periodic);
}

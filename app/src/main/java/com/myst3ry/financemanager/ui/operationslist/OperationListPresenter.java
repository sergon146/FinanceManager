package com.myst3ry.financemanager.ui.operationslist;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.OperationListUseCase;
import com.myst3ry.model.PeriodicOperation;

@InjectViewState
public class OperationListPresenter extends BasePresenter<OperationListView> {

    private final OperationListUseCase useCase;

    public OperationListPresenter(OperationListUseCase useCase) {
        this.useCase = useCase;
    }

    public void setShowPeriodic(boolean isShowPeriodic) {
        if (isShowPeriodic) {
            bind(onUi(useCase.getPeriodicOperations())
                    .doOnSubscribe((p) -> getViewState().showProgressBar())
                    .doOnNext((p) -> getViewState().hideProgressBar())
                    .subscribe(periodicOperation -> {
                        getViewState().showPeriodicOperations(periodicOperation);
                    }));
        }
    }

    public void togglePeriodic(boolean isActive, PeriodicOperation periodic) {
        bind(onUi(useCase.togglePeriodic(isActive, periodic)).subscribe(() -> {
        }, throwable -> getViewState().periodicToggleError(!isActive, periodic)));
    }
}

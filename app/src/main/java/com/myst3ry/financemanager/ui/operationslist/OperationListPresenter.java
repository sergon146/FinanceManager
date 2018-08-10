package com.myst3ry.financemanager.ui.operationslist;

import com.arellomobile.mvp.InjectViewState;
import com.example.delegateadapter.delegate.diff.IComparableItem;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.OperationListUseCase;
import com.myst3ry.model.AccountItemType;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class OperationListPresenter extends BasePresenter<OperationListView> {

    private final OperationListUseCase useCase;
    private List<IComparableItem> operations = new ArrayList<>();

    public OperationListPresenter(OperationListUseCase useCase) {
        this.useCase = useCase;
    }

    public void loadByType(AccountItemType type) {
        switch (type) {
            case FEED:
                getViewState().setScreenTitle(R.string.all_operations);
                loadFeed();
                break;
            case PERIODIC:
                getViewState().setScreenTitle(R.string.perioidc_operation_title);
                loadPeriodic();
                break;
            case PATTERN:
                getViewState().setScreenTitle(R.string.template_title);
                loadTemplates();
                break;
        }
    }

    public void loadByAccountId(Long id) {
        bind(onUi(useCase.getAccount(id)).subscribe(acc ->
                getViewState().setScreenTitle(acc.getTitle())));

        bind(onUi(useCase.getOperations(id))
                .doOnSubscribe((p) -> getViewState().showProgressBar())
                .doOnNext((p) -> getViewState().hideProgressBar())
                .doOnError(th -> getViewState().showEmpty())
                .subscribe(operations ->
                        handleOperationList(new ArrayList<>(operations))));
    }

    private void loadFeed() {
        bind(onUi(useCase.getFeed())
                .doOnSubscribe((p) -> getViewState().showProgressBar())
                .doOnNext((p) -> getViewState().hideProgressBar())
                .doOnError(th -> getViewState().showEmpty())
                .subscribe(operations ->
                        handleOperationList(new ArrayList<>(operations))));
    }

    private void loadPeriodic() {
        bind(onUi(useCase.getPeriodicOperations())
                .doOnSubscribe((p) -> getViewState().showProgressBar())
                .doOnNext((p) -> getViewState().hideProgressBar())
                .doOnError(th -> getViewState().showEmpty())
                .subscribe(periodicOperation ->
                        handleOperationList(new ArrayList<>(periodicOperation))));
    }

    private void loadTemplates() {
        bind(onUi(useCase.getTemplates())
                .doOnSubscribe((p) -> getViewState().showProgressBar())
                .doOnNext((p) -> getViewState().hideProgressBar())
                .doOnError(th -> getViewState().showEmpty())
                .subscribe(templates -> handleOperationList(new ArrayList<>(templates))));
    }

    private void handleOperationList(List<IComparableItem> operation) {
        operations.addAll(operation);
        if (operation.isEmpty()) {
            getViewState().showEmpty();
        } else {
            getViewState().hideEmpty();
        }

        getViewState().showOperations(operation);
    }

    public void togglePeriodic(boolean isActive, int position) {
        PeriodicOperation periodic = (PeriodicOperation) operations.get(position);
        bind(onUi(useCase.togglePeriodic(isActive, periodic))
                .subscribe(() -> {
                }, throwable ->
                        getViewState().periodicToggleError(!isActive, periodic)));
    }

    public void onPeriodicDelete(int position) {
        PeriodicOperation periodic = (PeriodicOperation) operations.get(position);
        bind(onUi(useCase.deletePeriodic(periodic)).subscribe(() -> {
        }, throwable -> getViewState().showToast(R.string.error)));
    }

    public void onOperaionDelete(int position) {
        Operation operation = (Operation) operations.get(position);
        bind(onUi(useCase.deleteOperaion(operation)).subscribe(() -> {
        }, throwable -> getViewState().showToast(R.string.error)));
    }

    public void onOperationEdit(int position) {
        Operation operation = (Operation) operations.get(position);
        getViewState().showEditDialog(operation);
    }

    public void showAddDialog() {
        getViewState().showAddDialog();
    }
}

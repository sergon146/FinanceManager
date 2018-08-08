package com.myst3ry.financemanager.ui.dialogs.addoperation;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.dialog.BaseDialogPresenter;
import com.myst3ry.financemanager.usecase.AddOperationUseCase;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;

@InjectViewState
public class AddOperationPresenter extends BaseDialogPresenter<AddOperationView> {

    private final AddOperationUseCase useCase;

    public AddOperationPresenter(AddOperationUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void attachView(AddOperationView view) {
        super.attachView(view);
        bind(onUi(useCase.getAccounts()).subscribe(accounts ->
                getViewState().showAccounts(accounts)));
    }

    public void performOperation(Account account,Operation operation, PeriodicOperation periodic) {
        operation.setCurrencyType(account.getCurrencyType());
        bind(onUi(useCase.addOperation(operation, periodic)).subscribe(
                () -> getViewState().successPerform(),
                throwable -> {
                    getViewState().showLongToast(R.string.error_create_operation);
                    throwable.printStackTrace();
                }));
    }

    @Override
    protected String getScreenTag() {
        return "AddOperationPresenter";
    }
}

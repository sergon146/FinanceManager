package com.myst3ry.financemanager.ui.dialogs.addoperation;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.dialog.BaseDialogPresenter;
import com.myst3ry.financemanager.usecase.AddOperationUseCase;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;
import com.myst3ry.model.PeriodicOperation;

import java.math.BigDecimal;

@InjectViewState
public class AddOperationPresenter extends BaseDialogPresenter<AddOperationView> {

    private final AddOperationUseCase useCase;
    private Operation operation;

    public AddOperationPresenter(AddOperationUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void attachView(AddOperationView view) {
        super.attachView(view);
    }

    public void loadData() {
        bind(onUi(useCase.getAccounts()).subscribe(accounts ->
                getViewState().showAccounts(accounts)));
    }

    public void loadOperation(long operationId) {
        bind(onUi(useCase.loadOperation(operationId)).subscribe(operation -> {
            this.operation = operation;
            getViewState().showOperation(operation);
        }, throwable -> {
            getViewState().showToast(R.string.error);
            getViewState().dismiss();
            throwable.printStackTrace();
        }));
    }

    public void performOperation(Account account,
                                 Operation operation,
                                 PeriodicOperation periodic,
                                 boolean isSavteToTemplate) {
        BigDecimal deltaAmount = operation.getType() == OperationType.INCOME
                ? operation.getAmount()
                : operation.getAmount().negate();

        if (this.operation != null) {
            operation.setId(this.operation.getId());
            deltaAmount = this.operation.getAmount().subtract(operation.getAmount());
            operation.setAccountId(this.operation.getAccountId());
            account = this.operation.getAccount();
        }
        operation.setAccount(account);
        operation.setCurrencyType(account.getCurrencyType());
        bind(onUi(useCase.addOperation(operation, periodic, deltaAmount)).subscribe(
                () -> getViewState().successPerform(),
                throwable -> {
                    getViewState().showLongToast(R.string.error_create_operation);
                    throwable.printStackTrace();
                }));
        if (isSavteToTemplate) {
            bind(onUi(useCase.saveTemplate(operation)).subscribe(
                    () -> {
                    },
                    throwable ->
                            getViewState().showToast(R.string.template_error)));
        }
    }

    @Override
    protected String getScreenTag() {
        return "TemplatePresenter";
    }
}

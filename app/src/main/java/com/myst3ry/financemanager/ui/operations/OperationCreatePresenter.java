package com.myst3ry.financemanager.ui.operations;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.OperationCreateUseCase;
import com.myst3ry.model.Account;
import com.myst3ry.model.Operation;


@InjectViewState
public class OperationCreatePresenter extends BasePresenter<OperationCreateView> {
    private final OperationCreateUseCase useCase;
    private Account currentAccount;

    public OperationCreatePresenter(OperationCreateUseCase useCase) {
        this.useCase = useCase;
    }

    public void setAccountId(long id) {
        bind(onUi(useCase.getAccount(id)).subscribe(account -> {
            currentAccount = account;
            getViewState().showAccountData(account);
        }));
    }

    public void performOperation(Operation operation) {
        operation.setCurrencyType(currentAccount.getCurrencyType());
        bind(onUi(useCase.addOperation(operation)).subscribe(
                () -> getViewState().successPerform(),
                throwable -> {
                    getViewState().showLongToast(R.string.error_create_operation);
                    throwable.printStackTrace();
                }));
    }
}

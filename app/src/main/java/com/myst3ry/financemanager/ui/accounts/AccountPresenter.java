package com.myst3ry.financemanager.ui.accounts;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.AccountUseCase;
import com.myst3ry.model.CurrencyType;

@InjectViewState
public class AccountPresenter extends BasePresenter<AccountView> {
    private final AccountUseCase useCase;

    public AccountPresenter(AccountUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void attachView(AccountView view) {
        super.attachView(view);
        bind(onUi(useCase.getAccounts(CurrencyType.RUB, CurrencyType.USD))
                .subscribe(accounts -> getViewState().showAccounts(accounts)));
    }

}

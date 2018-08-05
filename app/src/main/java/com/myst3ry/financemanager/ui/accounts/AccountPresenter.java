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

        bind(onUi(useCase.getBalanceSum(CurrencyType.RUB, CurrencyType.USD)).subscribe(pair -> {
                    getViewState().showPrimaryBalance(pair.first);
                    getViewState().showAdditionalBalance(pair.second);
                }
        ));

        bind(onUi(useCase.getAccounts()).subscribe(accounts ->
                getViewState().showAccounts(accounts)));


    }
}

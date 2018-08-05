package com.myst3ry.financemanager.ui.balance;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.BalanceUseCase;
import com.myst3ry.model.Account;
import com.myst3ry.model.Balance;

@InjectViewState
public class BalancePresenter extends BasePresenter<BalanceView> {

    private final BalanceUseCase useCase;

    public BalancePresenter(BalanceUseCase useCase) {
        this.useCase = useCase;
    }

    public void setCurrentUuid(long id) {
        bind(onUi(useCase.getAccountBalance(id)).subscribe(account -> {
            getViewState().setupTitle(account.getTitle());
            loadBalances(account);
            loadOperations(account);
        }));
    }

    private void loadBalances(Account account) {
        Balance balance = new Balance(account.getCurrencyType(), account.getBalance());
        getViewState().showMainBalance(balance);
        bind(onUi(useCase.getExchangeBalance(balance)).subscribe(additional ->
                getViewState().showAdditionalBalance(additional)));
    }

    private void loadOperations(Account account) {
        bind(onUi(useCase.getOperations(account))
                .subscribe(Operations -> getViewState().showOperations(Operations)));
    }
}

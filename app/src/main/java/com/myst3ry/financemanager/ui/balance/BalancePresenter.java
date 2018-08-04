package com.myst3ry.financemanager.ui.balance;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.BalanceUseCase;

import java.math.BigDecimal;
import java.util.UUID;

@InjectViewState
public class BalancePresenter extends BasePresenter<BalanceView> {

    private final BalanceUseCase useCase;

    public BalancePresenter(BalanceUseCase useCase) {
        this.useCase = useCase;
    }

    public void setCurrentUuid(UUID uuid) {
        bind(onUi(useCase.getAccountBalance(uuid)).subscribe(account -> {
            loadTransactions(account);
            getViewState().showTotalBalance(account);
            getExchangeBalance(account.getBalance());
        }));
    }

    private void loadTransactions(Account account) {
        bind(onUi(useCase.getTransactions(account))
                .subscribe(transactions -> getViewState().showTransactions(transactions)));
    }

    public void getExchangeBalance(BigDecimal defaultAmount) {
        CurrencyType type = CurrencyType.USD;
        bind(onUi(useCase.getExchangeBalance(defaultAmount, type)).subscribe(amount ->
                getViewState().showExchangedBalance(amount, type)));
    }
}

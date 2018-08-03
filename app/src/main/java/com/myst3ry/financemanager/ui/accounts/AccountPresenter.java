package com.myst3ry.financemanager.ui.accounts;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.data.remote.model.Valute;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.AccountUseCase;

@InjectViewState
public class AccountPresenter extends BasePresenter<AccountView> {
    private final AccountUseCase useCase;

    public AccountPresenter(AccountUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        bind(onUi(useCase.getAccounts())
                .subscribe(getViewState()::showAccounts));

        bind(onUi(useCase.getExchangeRate()).subscribe(val -> {
            saveActualExchangeRates(val);
            getViewState().showToast(val.getUSD().getValue() + "");
        }));
    }

    private void saveActualExchangeRates(final Valute valutes) {
        //        RatesStorage.getInstance().saveUsdRate(context, valutes != null ? valutes.getUSD().getValue() : 0f);
    }

}

package com.myst3ry.financemanager.ui.accounts;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.AccountUseCase;
import com.myst3ry.model.AccountBaseItem;
import com.myst3ry.model.AccountItemType;
import com.myst3ry.model.CurrencyType;

import java.util.ArrayList;
import java.util.List;

import static com.myst3ry.financemanager.ui.main.screens.Screens.OPERATION_LIST_SCREEN;

@InjectViewState
public class AccountPresenter extends BasePresenter<AccountView> {
    private final AccountUseCase useCase;
    private List<AccountBaseItem> accounts = new ArrayList<>();
    private int lastActivePosition = 0;

    public AccountPresenter(AccountUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        bind(onUi(useCase.getAccounts(CurrencyType.RUB, CurrencyType.USD))
                .subscribe(accounts -> {
                    this.accounts.clear();
                    this.accounts.addAll(new ArrayList<>(accounts));
                    this.accounts.get(lastActivePosition).setSelected(true);
                    handleShowAccounts(accounts, lastActivePosition);
                }));
    }

    public void openDetailScreen(Object data, int pos) {
        if (pos == -1) {
            return;
        }

        if (!(data instanceof AccountItemType)) {
            data = accounts.get(pos);
        }

        accounts.get(lastActivePosition).setSelected(false);
        lastActivePosition = pos;
        accounts.get(pos).setSelected(true);

        handleShowAccounts(accounts, lastActivePosition);
        getViewState().openScreen(OPERATION_LIST_SCREEN, data, false);
    }

    private void handleShowAccounts(List<AccountBaseItem> accounts, int lastActivePosition) {
        if (accounts.isEmpty()) {
            getViewState().showEmpty();
        } else {
            getViewState().hideEmpty();
            getViewState().showAccounts(new ArrayList<>(accounts), lastActivePosition);
        }
    }
}

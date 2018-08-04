package com.myst3ry.financemanager.ui.transactions;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.calculations.model.TransactionType;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.TransactionCreateUseCase;

import java.util.UUID;

@InjectViewState
public class TransactionCratePresenter extends BasePresenter<TransactionCreateView> {
    private final TransactionCreateUseCase useCase;
    private Account currentAccount;

    public TransactionCratePresenter(TransactionCreateUseCase useCase) {
        this.useCase = useCase;
    }

    public void setUuid(UUID uuid) {
        bind(onUi(useCase.getAccount(uuid)).subscribe(account -> {
            currentAccount = account;
            getViewState().showAccountData(account);
        }));
    }

    public void performTransaction(Transaction transaction, float usdRate) {
        bind(onUi(useCase.addTransaction(currentAccount, transaction)).subscribe(
                () -> getViewState().successPerform(),
                t -> getViewState().showLongToast(R.string.error_create_transaction)));

        Calculations calcModule = Calculations.getInstance(currentAccount,
                usdRate);
        if (transaction.getType() == TransactionType.EXPENSE) {
            calcModule.expense(transaction);
        } else if (transaction.getType() == TransactionType.INCOME) {
            calcModule.income(transaction);
        }
    }
}

package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.TransactionRepository;
import com.myst3ry.financemanager.ui.transactions.TransactionCratePresenter;
import com.myst3ry.financemanager.usecase.TransactionCreateUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionCreateModule {

    @Provides
    static TransactionCreateUseCase provideTransactionCreateUseCase(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        return new TransactionCreateUseCase(accountRepository, transactionRepository);
    }

    @Provides
    static TransactionCratePresenter provideTransactionCratePresenter(
            TransactionCreateUseCase useCase) {
        return new TransactionCratePresenter(useCase);
    }
}

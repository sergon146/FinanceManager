package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.operations.OperationCreatePresenter;
import com.myst3ry.financemanager.ui.operationslist.OperationListPresenter;
import com.myst3ry.financemanager.usecase.OperationCreateUseCase;
import com.myst3ry.financemanager.usecase.OperationListUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class OperationModule {

    @Provides
    static OperationCreateUseCase provideOperationCreateUseCase(
            AccountRepository accountRepository,
            OperationRepository operationRepository) {
        return new OperationCreateUseCase(accountRepository, operationRepository);
    }

    @Provides
    static OperationCreatePresenter provideOperationCratePresenter(
            OperationCreateUseCase useCase) {
        return new OperationCreatePresenter(useCase);
    }

    @Provides
    static OperationListUseCase provideOperationListUseCase(OperationRepository operationRepository) {
        return new OperationListUseCase(operationRepository);
    }

    @Provides
    static OperationListPresenter provideOperationListPresenter(OperationListUseCase useCase) {
        return new OperationListPresenter(useCase);
    }
}

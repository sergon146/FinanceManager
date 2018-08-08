package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.operationslist.OperationListPresenter;
import com.myst3ry.financemanager.usecase.OperationListUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class OperationModule {

    @Provides
    static OperationListUseCase provideOperationListUseCase(OperationRepository operationRepository,
                                                            AccountRepository accountRepository) {
        return new OperationListUseCase(operationRepository, accountRepository);
    }

    @Provides
    static OperationListPresenter provideOperationListPresenter(OperationListUseCase useCase) {
        return new OperationListPresenter(useCase);
    }
}

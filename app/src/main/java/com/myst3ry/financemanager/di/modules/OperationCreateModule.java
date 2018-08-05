package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.operations.OperationCreatePresenter;
import com.myst3ry.financemanager.usecase.OperationCreateUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class OperationCreateModule {

    @Provides
    static OperationCreateUseCase provideOperationCreateUseCase(
            AccountRepository accountRepository,
            OperationRepository OperationRepository) {
        return new OperationCreateUseCase(accountRepository, OperationRepository);
    }

    @Provides
    static OperationCreatePresenter provideOperationCratePresenter(
            OperationCreateUseCase useCase) {
        return new OperationCreatePresenter(useCase);
    }
}

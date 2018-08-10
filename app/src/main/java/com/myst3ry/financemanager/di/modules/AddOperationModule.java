package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.repository.TemplateRepository;
import com.myst3ry.financemanager.ui.dialogs.addoperation.AddOperationPresenter;
import com.myst3ry.financemanager.usecase.AddOperationUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AddOperationModule {

    @Provides
    static AddOperationUseCase provideOperationCreateUseCase(
            AccountRepository accountRepository,
            OperationRepository operationRepository,
            TemplateRepository templateRepository) {
        return new AddOperationUseCase(accountRepository, operationRepository, templateRepository);
    }

    @Provides
    static AddOperationPresenter provideAddOperationPresenter(AddOperationUseCase useCase) {
        return new AddOperationPresenter(useCase);
    }
}

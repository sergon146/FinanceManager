package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.accounts.AccountPresenter;
import com.myst3ry.financemanager.usecase.AccountUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AccountModule {

    @Provides
    static AccountUseCase provideAccountUseCase(AccountRepository accountRepository,
                                                ExchangeRepository exchangeRepository,
                                                OperationRepository operationRepository) {
        return new AccountUseCase(accountRepository, exchangeRepository, operationRepository);
    }

    @Provides
    static AccountPresenter provideAccountPresenter(AccountUseCase useCase) {
        return new AccountPresenter(useCase);
    }
}

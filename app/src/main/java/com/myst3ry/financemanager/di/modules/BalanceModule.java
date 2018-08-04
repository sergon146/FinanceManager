package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.TransactionRepository;
import com.myst3ry.financemanager.ui.balance.BalancePresenter;
import com.myst3ry.financemanager.usecase.BalanceUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class BalanceModule {

    @Provides
    static BalanceUseCase provideBalanceUseCase(AccountRepository accountRepository,
                                                ExchangeRepository exchangeRepository,
                                                TransactionRepository transactionRepository) {
        return new BalanceUseCase(accountRepository, exchangeRepository, transactionRepository);
    }

    @Provides
    static BalancePresenter provideBalancePresenter(BalanceUseCase useCase) {
        return new BalancePresenter(useCase);
    }
}

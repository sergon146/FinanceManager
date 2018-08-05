package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.data.dao.AccountsDao;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.TransactionRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Provides
    static AccountsDao provideAccountDbStub() {
        return new AccountsDao();
    }

    @Singleton
    @Provides
    static AccountRepository provideAccountRepository(AccountsDao stub) {
        return new AccountRepository(stub);
    }

    @Singleton
    @Provides
    static ExchangeRepository provideExchangeRepository(ExchangeApi exchangeApi) {
        return new ExchangeRepository(exchangeApi);
    }

    @Singleton
    @Provides
    static TransactionRepository provideTransactionRepository() {
        return new TransactionRepository();
    }
}

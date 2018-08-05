package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.data.dao.ExchangeDao;
import com.myst3ry.financemanager.data.local.MainDatabase;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

    @Singleton
    @Provides
    static AccountRepository provideAccountRepository(MainDatabase database) {
        return new AccountRepository(database.accountDao());
    }

    @Singleton
    @Provides
    static ExchangeRepository provideExchangeRepository(ExchangeApi exchangeApi,
                                                        ExchangeDao exchangeDao) {
        return new ExchangeRepository(exchangeApi, exchangeDao);
    }

    @Singleton
    @Provides
    static OperationRepository provideOperationRepository(MainDatabase database) {
        return new OperationRepository(database.operationDao());
    }
}

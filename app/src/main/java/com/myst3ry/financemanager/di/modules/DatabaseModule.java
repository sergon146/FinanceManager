package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.App;
import com.myst3ry.financemanager.data.dao.AccountDao;
import com.myst3ry.financemanager.data.dao.ExchangeDao;
import com.myst3ry.financemanager.data.dao.OperationDao;
import com.myst3ry.financemanager.data.local.MainDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static MainDatabase provideMainDatabase() {
        return App.getInstance().getDatabase();
    }

    @Provides
    static OperationDao provideOperationDao(MainDatabase database) {
        return database.operationDao();
    }

    @Provides
    static AccountDao provideAccountDao(MainDatabase database) {
        return database.accountDao();
    }

    @Provides
    static ExchangeDao provideExchangeDao(MainDatabase database) {
        return database.exchangeDao();
    }
}

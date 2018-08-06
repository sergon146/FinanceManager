package com.myst3ry.financemanager.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.myst3ry.financemanager.data.dao.AccountDao;
import com.myst3ry.financemanager.data.dao.ExchangeDao;
import com.myst3ry.financemanager.data.dao.OperationAccountPeriodicDao;
import com.myst3ry.financemanager.data.dao.OperationDao;
import com.myst3ry.financemanager.data.dao.PeriodicDao;
import com.myst3ry.financemanager.utils.DatabaseDataStub;
import com.myst3ry.model.Account;
import com.myst3ry.model.ExchangeRate;
import com.myst3ry.model.Operation;
import com.myst3ry.model.PeriodicOperation;

import java.util.concurrent.Executors;

@Database(entities = {Operation.class, Account.class, ExchangeRate.class, PeriodicOperation.class},
        version = 1, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {
    private static MainDatabase instance;

    public static MainDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static MainDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, MainDatabase.class, "StarCoinBase.db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            MainDatabase database = getInstance(context);
                            database.accountDao().insert(DatabaseDataStub.getRurAccount());
                            database.accountDao().insert(DatabaseDataStub.getUsdAccount());

                            database.exchangeDao().insert(DatabaseDataStub.getRubUsdRate());
                            database.exchangeDao().insert(DatabaseDataStub.getUsdRubRate());
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract OperationDao operationDao();

    public abstract AccountDao accountDao();

    public abstract ExchangeDao exchangeDao();

    public abstract OperationAccountPeriodicDao operationAccountPeriodicDao();

    public abstract PeriodicDao periodicDao();
}

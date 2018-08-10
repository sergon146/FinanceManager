package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.myst3ry.model.ExchangeRate;

import io.reactivex.Flowable;

@Dao
public abstract class ExchangeDao extends BaseDao<ExchangeRate> {
    @Query("SELECT * FROM exchange WHERE exchangeCurrency = :currencyName")
    public abstract Flowable<ExchangeRate> getRateByType(String currencyName);
}

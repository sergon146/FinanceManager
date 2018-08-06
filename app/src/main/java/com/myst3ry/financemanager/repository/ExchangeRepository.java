package com.myst3ry.financemanager.repository;

import com.myst3ry.financemanager.data.dao.ExchangeDao;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.ExchangeRate;

import io.reactivex.Flowable;

public class ExchangeRepository extends BaseRepository {
    private final ExchangeApi exchangeApi;
    private final ExchangeDao exchangeDao;

    public ExchangeRepository(ExchangeApi exchangeApi, ExchangeDao exchangeDao) {
        this.exchangeApi = exchangeApi;
        this.exchangeDao = exchangeDao;
    }

    public Flowable<ExchangeRate> getExchangeRate(CurrencyType type) {
        return exchangeDao.getRateByType(type.name());
    }
}

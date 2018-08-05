package com.myst3ry.financemanager.repository;

import com.myst3ry.financemanager.data.dao.ExchangeDao;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.ExchangeRate;

import java.math.BigDecimal;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ExchangeRepository extends BaseRepository {
    private final ExchangeApi exchangeApi;
    private final ExchangeDao exchangeDao;

    public ExchangeRepository(ExchangeApi exchangeApi, ExchangeDao exchangeDao) {
        this.exchangeApi = exchangeApi;
        this.exchangeDao = exchangeDao;
    }

    private static String getFromTo(CurrencyType in, CurrencyType out) {
        return in.name() + "_" + out.name();
    }

    public Observable<BigDecimal> getExchangedAmount(BigDecimal amount, CurrencyType type) {
        return Observable.just(amount.divide(amount, BigDecimal.ROUND_HALF_UP));
    }

    public Flowable<ExchangeRate> getOutExchangeRate(CurrencyType type) {
        return exchangeDao.getRateByType(Utils.Currency.getOutCurrency(type).name());
    }

    public Flowable<ExchangeRate> getExchangeRate(CurrencyType type) {
        return exchangeDao.getRateByType(type.name());
    }
}

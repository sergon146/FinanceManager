package com.myst3ry.financemanager.repository;

import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.financemanager.data.remote.ExchangeApi;
import com.myst3ry.financemanager.data.remote.model.ApiResponse;
import com.myst3ry.financemanager.data.remote.model.Valute;

import java.math.BigDecimal;

import io.reactivex.Observable;


public class ExchangeRepository {
    private final ExchangeApi exchangeApi;
    private BigDecimal exchangeUsdRub = BigDecimal.valueOf(63.51);

    public ExchangeRepository(ExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
    }

    public Observable<BigDecimal> getExchangedAmount(BigDecimal amount, CurrencyType type) {
        return Observable.just(amount.divide(exchangeUsdRub, BigDecimal.ROUND_HALF_UP));
    }

    public Observable<Valute> getExchangeRate() {
        return exchangeApi.getActualExchangeRates()
                .map(ApiResponse::getValutes);
    }
}

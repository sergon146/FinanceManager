package com.myst3ry.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.myst3ry.model.converter.BigDecimalConverter;
import com.myst3ry.model.converter.DateConverter;

import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName = "exchange")
public class ExchangeRate {
    @PrimaryKey
    @NonNull
    private String defaultCurrency;
    private String exchangeCurrency;
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal rate;
    @TypeConverters(DateConverter.class)
    private Date date;

    public ExchangeRate(@NonNull String defaultCurrency, String exchangeCurrency, String jsonResponse) {
        this.defaultCurrency = defaultCurrency;
        this.exchangeCurrency = exchangeCurrency;
        String val = jsonResponse.substring(jsonResponse
                .lastIndexOf(':') + 1, jsonResponse.length() - 1);
        this.rate = BigDecimal.valueOf(Double.valueOf(val));
        this.date = new Date();
    }

    public ExchangeRate(String defaultCurrency,
                        String exchangeCurrency,
                        BigDecimal rate) {
        this.defaultCurrency = defaultCurrency;
        this.exchangeCurrency = exchangeCurrency;
        this.rate = rate;
        this.date = new Date();
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }

    public void setExchangeCurrency(String exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal exchangeRate) {
        this.rate = exchangeRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
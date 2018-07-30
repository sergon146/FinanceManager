package com.myst3ry.financemanager.network.model;

import com.google.gson.annotations.SerializedName;

public final class Valute {

    @SerializedName("USD")
    private USD mUSD;

    @SerializedName("EUR")
    private EUR mEUR;

    @SerializedName("UAH")
    private UAH mUAH;

    public EUR getEUR() {
        return mEUR;
    }

    public USD getUSD() {
        return mUSD;
    }

    public UAH getUAH() {
        return mUAH;
    }
}
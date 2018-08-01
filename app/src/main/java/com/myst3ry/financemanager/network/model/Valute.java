package com.myst3ry.financemanager.network.model;

import com.google.gson.annotations.SerializedName;

public final class Valute {

    @SerializedName("USD")
    private USD mUSD;

    public USD getUSD() {
        return mUSD;
    }
}
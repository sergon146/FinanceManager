package com.myst3ry.financemanager.network.model;

import com.google.gson.annotations.SerializedName;

public final class ApiResponse {

    @SerializedName("Timestamp")
    private String mTimestamp;

    @SerializedName("Date")
    private String mDate;

    @SerializedName("Valute")
    private Valute mValutes;

    public String getTimestamp() {
        return mTimestamp;
    }

    public String getDate() {
        return mDate;
    }

    public Valute getValutes() {
        return mValutes;
    }
}
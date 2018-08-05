package com.myst3ry.model.dto;

import com.google.gson.annotations.SerializedName;

public final class ApiResponse {

    @SerializedName("Timestamp")
    private String timestamp;

    @SerializedName("Date")
    private String date;

    @SerializedName("Valute")
    private Valute valutes;

    public String getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        return date;
    }

    public Valute getValutes() {
        return valutes;
    }
}
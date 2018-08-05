package com.myst3ry.model.dto;

import com.google.gson.annotations.SerializedName;

public final class USD {

    @SerializedName("Value")
    private Float value;

    @SerializedName("CharCode")
    private String currencyCode;

    public Float getValue() {
        if (value == null) {
            return 1f;
        }

        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
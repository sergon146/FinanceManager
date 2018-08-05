package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import java.math.BigDecimal;

public class BigDecimalConverter {

    @TypeConverter
    public static BigDecimal toBigDecimal(String decimalString) {
        return new BigDecimal(decimalString);
    }

    @TypeConverter
    public static String toString(BigDecimal decimal) {
        return decimal.toString();
    }
}

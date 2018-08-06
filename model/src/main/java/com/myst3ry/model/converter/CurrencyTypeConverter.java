package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import com.myst3ry.model.CurrencyType;

public class CurrencyTypeConverter {


    @TypeConverter
    public static CurrencyType toCategoryType(String name) {
        return CurrencyType.valueOf(name);
    }

    @TypeConverter
    public static String toString(CurrencyType type) {
        return type.name();
    }
}

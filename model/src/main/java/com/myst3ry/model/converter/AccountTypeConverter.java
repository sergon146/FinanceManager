package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import com.myst3ry.model.AccountType;

public class AccountTypeConverter {

    @TypeConverter
    public static AccountType toCategoryType(String name) {
        return AccountType.valueOf(name);
    }

    @TypeConverter
    public static String toString(AccountType type) {
        return type.name();
    }
}

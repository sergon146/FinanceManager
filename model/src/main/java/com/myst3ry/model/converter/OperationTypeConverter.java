package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import com.myst3ry.model.OperationType;

public class OperationTypeConverter {

    @TypeConverter
    public static OperationType toOperationType(String name) {
        return OperationType.valueOf(name);
    }

    @TypeConverter
    public static String toString(OperationType type) {
        return type.name();
    }
}

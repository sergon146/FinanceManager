package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import com.myst3ry.model.CategoryType;

public class CategoryTypeConverter {

    @TypeConverter
    public static CategoryType toCategoryType(String name) {
        return CategoryType.valueOf(name);
    }

    @TypeConverter
    public static String toString(CategoryType type) {
        return type.name();
    }
}

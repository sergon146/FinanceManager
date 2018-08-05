package com.myst3ry.model.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long toTimeStamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public Date toDate(Long timestamp) {
        return new Date(timestamp);
    }
}

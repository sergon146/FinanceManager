package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public abstract class BaseDao<T> {
    @Insert(onConflict = REPLACE)
    public abstract long insert(T obj);

    @Delete
    public abstract int delete(T obj);
}

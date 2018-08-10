package com.myst3ry.financemanager.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.myst3ry.model.Template;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class TemplateDao extends BaseDao<Template> {
    @Query("SELECT * FROM template WHERE isActive = 1 ORDER BY id DESC")
    public abstract Flowable<List<Template>> getAll();


    @Query("UPDATE template SET isActive = 0 WHERE id = :id")
    public abstract void delete(long id);
}

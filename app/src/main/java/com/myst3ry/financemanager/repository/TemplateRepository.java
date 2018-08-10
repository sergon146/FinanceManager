package com.myst3ry.financemanager.repository;

import com.myst3ry.financemanager.data.dao.TemplateDao;
import com.myst3ry.model.Template;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TemplateRepository extends BaseRepository {
    private final TemplateDao templateDao;

    public TemplateRepository(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    public Flowable<List<Template>> getAllTemplate() {
        return templateDao.getAll();
    }

    public Completable saveTemplate(Template template) {
        return Completable.fromAction(() -> templateDao.insert(template));
    }

    public Completable deleteTemplate(Template template) {
        return Completable.fromAction(() -> templateDao.delete(template.getId()));
    }
}

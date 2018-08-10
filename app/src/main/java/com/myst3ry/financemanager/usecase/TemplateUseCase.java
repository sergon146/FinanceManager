package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.TemplateRepository;
import com.myst3ry.model.Template;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TemplateUseCase {
    private final TemplateRepository templateRepository;

    public TemplateUseCase(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Flowable<List<Template>> getAllTemplate() {
        return templateRepository.getAllTemplate();
    }

    public Completable deleteTemplate(Template template) {
        return templateRepository.deleteTemplate(template);
    }
}

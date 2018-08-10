package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.TemplateRepository;
import com.myst3ry.financemanager.ui.dialogs.template.TemplatePresenter;
import com.myst3ry.financemanager.usecase.TemplateUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TemplateModule {
    @Provides
    static TemplateUseCase provideTemplateUseCase(TemplateRepository templateRepository) {
        return new TemplateUseCase(templateRepository);
    }

    @Provides
    static TemplatePresenter provideTemplatePresenter(TemplateUseCase useCase) {
        return new TemplatePresenter(useCase);
    }

}

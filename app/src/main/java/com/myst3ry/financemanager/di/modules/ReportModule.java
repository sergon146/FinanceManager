package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.report.ReportPresenter;
import com.myst3ry.financemanager.usecase.ReportUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ReportModule {

    @Provides
    static ReportUseCase provideReportUseCase(OperationRepository operationRepository) {
        return new ReportUseCase(operationRepository);
    }

    @Provides
    static ReportPresenter provideReportPresenter(ReportUseCase useCase) {
        return new ReportPresenter(useCase);
    }

}

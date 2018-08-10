package com.myst3ry.financemanager.ui.report;

import com.arellomobile.mvp.InjectViewState;
import com.myst3ry.financemanager.ui.base.BasePresenter;
import com.myst3ry.financemanager.usecase.ReportUseCase;

import java.util.Date;

@InjectViewState
public class ReportPresenter extends BasePresenter<ReportView> {
    private final ReportUseCase useCase;

    public ReportPresenter(ReportUseCase useCase) {
        this.useCase = useCase;
    }

    public void loadReport(Date startDate, Date endDate) {
        bind(onUi(useCase.getReport(startDate, endDate)).subscribe(reportData -> {
            if (reportData.isEmpty()) {
                getViewState().showEmpty();
            } else {
                getViewState().hideEmpty();
            }

            getViewState().showReport(reportData);
        }));
    }
}

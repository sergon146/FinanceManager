package com.myst3ry.financemanager.ui.report;

import com.myst3ry.financemanager.ui.base.BaseView;
import com.myst3ry.model.ReportData;

import java.util.List;

public interface ReportView extends BaseView {
    void showReport(List<ReportData> reportData);

    void showEmpty();

    void hideEmpty();
}

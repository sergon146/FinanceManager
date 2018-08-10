package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.model.ReportData;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;

public class ReportUseCase {
    private final OperationRepository operationRepository;

    public ReportUseCase(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Flowable<List<ReportData>> getReport(Date start, Date end) {
        return operationRepository.getReport(start, end);
    }
}

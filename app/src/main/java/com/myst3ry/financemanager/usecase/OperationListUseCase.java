package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.model.PeriodicOperation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class OperationListUseCase {

    private final OperationRepository operationRepository;

    public OperationListUseCase(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Flowable<List<PeriodicOperation>> getPeriodicOperations() {
        return operationRepository.getPeriodicOperations();
    }

    public Completable togglePeriodic(boolean isActive, PeriodicOperation periodic) {
        return operationRepository.togglePeriodic(isActive, periodic);
    }
}

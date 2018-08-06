package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.OperationRepository;

import io.reactivex.Flowable;

public class MainUseCase {
    private final OperationRepository repository;

    public MainUseCase(OperationRepository repository) {
        this.repository = repository;
    }

    public Flowable executeNecessaryPendingOperation() {
        return repository.executeNecessaryPendingOperation();
    }
}

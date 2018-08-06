package com.myst3ry.financemanager.usecase;

import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;

import io.reactivex.Flowable;

public class MainUseCase {
    private final OperationRepository repository;
    private final ExchangeRepository exchangeRepository;

    public MainUseCase(OperationRepository repository, ExchangeRepository exchangeRepository) {
        this.repository = repository;
        this.exchangeRepository = exchangeRepository;
    }

    public Flowable executeNecessaryPendingOperation() {
        return repository.executeNecessaryPendingOperation();
    }
}

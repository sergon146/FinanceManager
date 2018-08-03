package com.myst3ry.financemanager.repository;

import com.myst3ry.calculations.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TransactionRepository {
    private List<Transaction> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();
    }

    public Observable<List<Transaction>> getTransactions() {
        return Observable.just(transactions);
    }

    public Completable addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return Completable.complete();
    }
}

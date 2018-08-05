package com.myst3ry.financemanager.repository;

import com.myst3ry.calculations.model.CategoryType;
import com.myst3ry.calculations.model.CurrencyType;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.calculations.model.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class TransactionRepository {
    private List<Transaction> transactions;

    public TransactionRepository() {
        transactions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            transactions.add(getRandomTransaction());
        }
    }

    public Observable<List<Transaction>> getTransactions() {
        return Observable.just(transactions);
    }

    public Completable addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return Completable.complete();
    }

    private Transaction getRandomTransaction() {
        Random random = new Random();
        return Transaction.newBuilder()
                .setAmount(new BigDecimal(random.nextInt(50000)))
                .setCategory(CategoryType.values()

                        [random.nextInt(CategoryType.values().length)].name().toLowerCase())
                .setCurrencyType(CurrencyType.values()
                        [random.nextInt(CurrencyType.values().length)])
                .setTransactionType(TransactionType.values()
                        [random.nextInt(TransactionType.values().length)])
                .build();
    }
}

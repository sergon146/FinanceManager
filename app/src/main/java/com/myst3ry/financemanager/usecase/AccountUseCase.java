package com.myst3ry.financemanager.usecase;

import android.util.Pair;

import com.myst3ry.financemanager.repository.AccountRepository;
import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.model.Account;
import com.myst3ry.model.AccountBaseItem;
import com.myst3ry.model.Balance;
import com.myst3ry.model.CurrencyType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class AccountUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRepository exchangeRepository;
    private final OperationRepository operationRepository;

    public AccountUseCase(AccountRepository accountRepository,
                          ExchangeRepository exchangeRepository,
                          OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.exchangeRepository = exchangeRepository;
        this.operationRepository = operationRepository;
    }

    public Flowable<List<AccountBaseItem>> getAccounts(CurrencyType primaryType,
                                                       CurrencyType additionalType) {
        return Flowable.combineLatest(
                exchangeRepository.getExchangeRate(primaryType),
                exchangeRepository.getExchangeRate(additionalType),
                accountRepository.getAccounts(),
                operationRepository.getTotalOperations(),
                operationRepository.getTotalPeriodicCount(),
                operationRepository.getActivePeriodicCount(),
                (rate, addRate, accounts, totalOperations, totalPeriodic, activatedPeriodic) -> {

                    Pair<Balance, Balance> balance = Utils.Balances
                            .getBalanceSum(primaryType, rate, addRate, accounts);
                    return prepareAccounts(accounts, balance, totalOperations, totalPeriodic,
                            activatedPeriodic);
                });

    }

    private List<AccountBaseItem> prepareAccounts(List<Account> accounts,
                                                  Pair<Balance, Balance> balance,
                                                  Long totalOperations,
                                                  Long totalPeriodic,
                                                  Long activatedPeriodic) {

        List<AccountBaseItem> accountItems = new ArrayList<>(accounts);
        accountItems.add(Utils.DataStub
                .getFeedAccount(balance.first, balance.second, totalOperations));
        accountItems.add(Utils.DataStub.getPeriodicAccount(totalPeriodic, activatedPeriodic));
        accountItems.add(Utils.DataStub.getPatternAccount());
        return accountItems;
    }
}

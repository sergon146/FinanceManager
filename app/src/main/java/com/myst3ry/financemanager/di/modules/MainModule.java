package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.about.AboutFragment;
import com.myst3ry.financemanager.ui.accounts.AccountsFragment;
import com.myst3ry.financemanager.ui.balance.BalanceFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.ui.main.MainPresenter;
import com.myst3ry.financemanager.ui.operations.OperationCreateFragment;
import com.myst3ry.financemanager.ui.operationslist.OperationListFragment;
import com.myst3ry.financemanager.usecase.MainUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @Provides
    static MainUseCase provideMainUseCase(OperationRepository operationRepository) {
        return new MainUseCase(operationRepository);
    }

    @Provides
    static MainPresenter provideMainPresenter(MainUseCase useCase) {
        return new MainPresenter(useCase);
    }

    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract AccountsFragment contributeAccountFragment();

    @ContributesAndroidInjector(modules = BalanceModule.class)
    abstract BalanceFragment contributeBalanceFragment();

    @ContributesAndroidInjector(modules = OperationModule.class)
    abstract OperationCreateFragment contributeOperationCreateFragment();

    @ContributesAndroidInjector(modules = OperationModule.class)
    abstract OperationListFragment contributeOperationListFragment();

    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment contributeAboutFragment();

    @ContributesAndroidInjector
    abstract SelectionDialogFragment contributeSelectionDialogFragment();
}

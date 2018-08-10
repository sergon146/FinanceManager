package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.repository.ExchangeRepository;
import com.myst3ry.financemanager.repository.OperationRepository;
import com.myst3ry.financemanager.ui.about.AboutFragment;
import com.myst3ry.financemanager.ui.accounts.AccountsFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.ui.dialogs.addoperation.AddOperationDialog;
import com.myst3ry.financemanager.ui.dialogs.template.TemplateDialog;
import com.myst3ry.financemanager.ui.main.MainPresenter;
import com.myst3ry.financemanager.ui.operationslist.OperationListFragment;
import com.myst3ry.financemanager.usecase.MainUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @Provides
    static MainUseCase provideMainUseCase(OperationRepository operationRepository,
                                          ExchangeRepository exchangeRepository) {
        return new MainUseCase(operationRepository, exchangeRepository);
    }

    @Provides
    static MainPresenter provideMainPresenter(MainUseCase useCase) {
        return new MainPresenter(useCase);
    }

    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract AccountsFragment contributeAccountFragment();

    @ContributesAndroidInjector(modules = OperationModule.class)
    abstract OperationListFragment contributeOperationListFragment();

    @ContributesAndroidInjector(modules = AddOperationModule.class)
    abstract AddOperationDialog contributeAddOperationDialog();

    @ContributesAndroidInjector(modules = TemplateModule.class)
    abstract TemplateDialog contributeTemplateDialog();

    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment contributeAboutFragment();

    @ContributesAndroidInjector
    abstract SelectionDialogFragment contributeSelectionDialogFragment();
}

package com.myst3ry.financemanager.di.modules;

import com.myst3ry.financemanager.ui.about.AboutFragment;
import com.myst3ry.financemanager.ui.accounts.AccountFragment;
import com.myst3ry.financemanager.ui.balance.BalanceFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.ui.main.MainPresenter;
import com.myst3ry.financemanager.ui.transactions.TransactionCreateFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @Provides
    static MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @ContributesAndroidInjector(modules = AccountModule.class)
    abstract AccountFragment contributeAccountFragment();

    @ContributesAndroidInjector(modules = BalanceModule.class)
    abstract BalanceFragment contributeBalanceFragment();

    @ContributesAndroidInjector(modules = TransactionCreateModule.class)
    abstract TransactionCreateFragment contributeTransactionCreateFragment();

    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment contributeAboutFragment();

    @ContributesAndroidInjector
    abstract SelectionDialogFragment contributeSelectionDialogFragment();
}

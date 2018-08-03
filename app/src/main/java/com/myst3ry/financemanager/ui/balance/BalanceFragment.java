package com.myst3ry.financemanager.ui.balance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.main.screens.Screens;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;

import java.math.BigDecimal;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public final class BalanceFragment extends BaseFragment<BalancePresenter>
        implements BalanceView {
    private static final String UUID_KEY = "UUID_KEY";

    @BindView(R.id.rur_balance)
    TextView mainCurBalanceTextView;
    @BindView(R.id.usd_balance)
    TextView secondCurBalanceTextView;

    @Inject
    @InjectPresenter
    public BalancePresenter presenter;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private UUID accountUUid;

    @Override
    @ProvidePresenter
    protected BalancePresenter providePresenter() {
        return presenter;
    }

    public static BalanceFragment newInstance(UUID accountUuid) {
        final BalanceFragment fragment = new BalanceFragment();
        final Bundle args = new Bundle();
        args.putSerializable(UUID_KEY, accountUuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            accountUUid = (UUID) getArguments().getSerializable(UUID_KEY);
            getPresenter().setCurrentUuid(accountUUid);
        }
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @OnClick(R.id.fab_add)
    void onFabClick() {
        activity.openScreen(Screens.TRANSACTIONS_SCREEN, accountUUid, false);
    }

    @Override
    public void showTotalBalance(Account account) {
        setScreenTitle(account.getTitle());
        mainCurBalanceTextView.setText(formatterFactory.create(account.getCurrencyType())
                .formatBalance(account.getBalance()));
    }

    @Override
    public void showExchangedBalance(BigDecimal amount, CurrencyType type) {
        secondCurBalanceTextView.setText(formatterFactory.create(type).formatBalance(amount));
    }

    @Override
    public String getScreenTag() {
        return "BalanceFragment";
    }
}

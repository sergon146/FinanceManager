package com.myst3ry.financemanager.ui.transactions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.data.local.RatesStorage;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public final class TransactionCreateFragment extends BaseFragment<TransactionCratePresenter>
        implements TransactionCreateView {

    private static final String ARG_ACCOUNT_UUID = "ARG_ACCOUNT_UUID";

    @BindView(R.id.account_title)
    TextView title;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.transaction_type)
    TextView transactionTextView;
    @BindView(R.id.currency_type)
    TextView currencyTextView;
    @BindView(R.id.category_label)
    TextView categoryTextView;
    @BindView(R.id.et_amount)
    EditText amountEditText;

    @Inject
    @InjectPresenter
    TransactionCratePresenter presenter;

    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();

    @State
    CurrencyType currentCurrencyType;
    @State
    TransactionType currentTransactionType;
    @State
    int transactionIndex, currencyIndex, categoryIndex;

    private ArrayList<String> accountTitles, transactionTitles, currencyTitles, categoryTitles;

    public static TransactionCreateFragment newInstance(UUID accountUuid) {
        final TransactionCreateFragment fragment = new TransactionCreateFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT_UUID, accountUuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @ProvidePresenter
    protected TransactionCratePresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setScreenTitle(R.string.new_transaction_title);

        if (getArguments() != null) {
            getPresenter().setUuid((UUID) getArguments().getSerializable(ARG_ACCOUNT_UUID));
        }

        initTitlesLists();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_create, container, false);
    }

    @Override
    protected void prepareViews() {
        setScreenTitle(R.string.new_transaction_title);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                setDefaultValues();
    }

    private void initTitlesLists() {
        transactionTitles = Utils.getTransactionTitles(activity);
        currencyTitles = Utils.getCurrencyTitles(activity);
        categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.arr_expense_categories)));
    }

    private void setDefaultValues() {
        transactionTextView.setText(transactionTitles.get(transactionIndex));
        currencyTextView.setText(CurrencyType.values()[currencyIndex].name());
        categoryTextView.setText(categoryTitles.get(categoryIndex));
    }

    @OnClick( {R.id.transaction_type_title, R.id.transaction_type})
    void selectTransactionType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(transactionTitles, transactionIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            currentTransactionType = TransactionType.values()[selectedIndex];
            final String type = transactionTitles.get(selectedIndex);
            replaceCategoryList(type);
            transactionTextView.setText(type);
            transactionIndex = selectedIndex;
        });
        dialog.show(activity.getSupportFragmentManager(), null);
    }

    @OnClick( {R.id.currency_type_title, R.id.currency_type})
    void selectCurrencyType() {
        final SelectionDialogFragment dialog =
                SelectionDialogFragment.newInstance(currencyTitles, currencyIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            currentCurrencyType = CurrencyType.values()[selectedIndex];
            currencyTextView.setText(currentCurrencyType.name());
            currencyIndex = selectedIndex;
        });
        dialog.show(activity.getSupportFragmentManager(), null);
    }

    @OnClick( {R.id.category_title, R.id.category_label})
    void selectCategoryLabel() {
        final SelectionDialogFragment dialog =
                SelectionDialogFragment.newInstance(categoryTitles, categoryIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            categoryTextView.setText(categoryTitles.get(selectedIndex));
            categoryIndex = selectedIndex;
        });
        dialog.show(activity.getSupportFragmentManager(), null);
    }

    private void replaceCategoryList(final String type) {
        if (type.equalsIgnoreCase(getString(R.string.dialog_title_expense))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_expense_categories)));
        } else if (type.equalsIgnoreCase(getString(R.string.dialog_title_income))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_income_categories)));
        }

        categoryIndex = 0;
        categoryTextView.setText(categoryTitles.get(categoryIndex));
    }

    @OnClick(R.id.add)
    void onAddClick() {
        createTransaction();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            manager.popBackStack();
        }
    }

    private void createTransaction() {
        final String amount = amountEditText.getText().toString();
        if (!amount.isEmpty()) {
            final Transaction transaction = Transaction.newBuilder()
                    .setAmount(new BigDecimal(amount))
                    .setCurrencyType(currentCurrencyType)
                    .setTransactionType(currentTransactionType)
                    .setCategory(categoryTextView.getText().toString())
                    .build();
            getPresenter().performTransaction(transaction,
                    RatesStorage.getInstance().getUsdRate(activity));
        } else {
            showToast(getString(R.string.err_empty_field));
        }
    }

    @Override
    public void showAccountData(Account account) {
        title.setText(account.getTitle());
        amount.setText(formatterFactory.create(account.getCurrencyType())
                .formatBalance(account.getBalance()));
    }

    @Override
    public void successPerform() {
        getFragmentManager().popBackStack();
        showToast(R.string.transaction_success);
    }

    @Override
    public String getScreenTag() {
        return "TransactionCreateFragment";
    }
}

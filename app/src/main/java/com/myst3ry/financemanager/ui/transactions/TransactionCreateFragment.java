package com.myst3ry.financemanager.ui.transactions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.myst3ry.calculations.Calculations;
import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.TransactionType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.calculations.model.Transaction;
import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.data.local.AccountsDbStub;
import com.myst3ry.financemanager.data.local.RatesStorage;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public final class TransactionCreateFragment extends BaseFragment {

    public static final String TAG = TransactionCreateFragment.class.getSimpleName();
    private static final String ARG_ACCOUNT_INDEX = BuildConfig.APPLICATION_ID + "arg.ACCOUNT_INDEX";

    @BindView(R.id.account_type)
    TextView accountTextView;
    @BindView(R.id.transaction_type)
    TextView transactionTextView;
    @BindView(R.id.currency_type)
    TextView currencyTextView;
    @BindView(R.id.category_label)
    TextView categoryTextView;
    @BindView(R.id.et_amount)
    EditText amountEditText;

    @State
    CurrencyType currentCurrencyType;
    @State
    TransactionType currentTransactionType;
    @State
    int accountIndex, transactionIndex, currencyIndex, categoryIndex;

    private ArrayList<String> accountTitles, transactionTitles, currencyTitles, categoryTitles;

    public static TransactionCreateFragment newInstance(final int accountIndex) {
        final TransactionCreateFragment fragment = new TransactionCreateFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ACCOUNT_INDEX, accountIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setScreenTitle(R.string.new_transaction_title);

        if (getArguments() != null) {
            accountIndex = getArguments().getInt(ARG_ACCOUNT_INDEX, 0);
        }

        initTitlesLists();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    @Override
    public String getScreenTag() {
        return "TransactionCreateFragment";
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        final MenuItem itemSettings = menu.findItem(R.id.action_settings);
        final MenuItem itemAbout = menu.findItem(R.id.action_about);
        itemSettings.setVisible(false);
        itemAbout.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_transaction, menu);
    }


    private void initTitlesLists() {
        accountTitles = getAccountTitles();
        transactionTitles = Utils.getTransactionTitles(activity);
        currencyTitles = Utils.getCurrencyTitles(activity);
        categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.arr_expense_categories)));
    }

    private void setDefaultValues() {
        accountTextView.setText(accountTitles.get(accountIndex));
        transactionTextView.setText(transactionTitles.get(transactionIndex));
        currencyTextView.setText(CurrencyType.values()[currencyIndex].name());
        categoryTextView.setText(categoryTitles.get(categoryIndex));
    }

    private ArrayList<String> getAccountTitles() {
        final List<Account> accounts = AccountsDbStub.getInstance().getAccounts();
        final ArrayList<String> titles = new ArrayList<>();

        for (final Account account : accounts) {
            titles.add(account.getTitle());
        }

        return titles;
    }

    @OnClick( {R.id.account_type_title, R.id.account_type})
    void selectAccountType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(accountTitles, accountIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            accountTextView.setText(accountTitles.get(selectedIndex));
            accountIndex = selectedIndex;
        });
        dialog.show(activity.getSupportFragmentManager(), null);
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

    private void createTransaction() {
        final String amount = amountEditText.getText().toString();
        if (!amount.isEmpty()) {
            final Transaction transaction = Transaction.newBuilder()
                    .setAmount(new BigDecimal(amount))
                    .setCurrencyType(currentCurrencyType)
                    .setTransactionType(currentTransactionType)
                    .setCategory(categoryTextView.getText().toString())
                    .build();
            performTransaction(transaction);
        } else {
            showToast(getString(R.string.err_empty_field));
        }
    }

    private void performTransaction(final Transaction transaction) {
        final Account currentAccount = AccountsDbStub.getInstance().getAccount(accountIndex);
        Calculations calcModule = Calculations.getInstance(currentAccount,
                RatesStorage.getInstance().getUsdRate(activity));
        if (transaction.getTransactionType() == TransactionType.EXPENSE) {
            calcModule.expense(transaction);
        } else if (transaction.getTransactionType() == TransactionType.INCOME) {
            calcModule.income(transaction);
        }
        //+ save into db
        activity.getSupportFragmentManager().popBackStackImmediate();
    }
}

package com.myst3ry.financemanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.myst3ry.calculations.CurrencyType;
import com.myst3ry.calculations.model.Account;
import com.myst3ry.financemanager.BuildConfig;
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.db.AccountsDbStub;
import com.myst3ry.financemanager.ui.dialog.SelectionDialogFragment;
import com.myst3ry.financemanager.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public final class TransactionCreateFragment extends BaseFragment {

    public static final String TAG = TransactionCreateFragment.class.getSimpleName();
    private static final String ARG_ACCOUNT_INDEX = BuildConfig.APPLICATION_ID + "arg.ACCOUNT_INDEX";

    @BindView(R.id.tv_account_type)
    TextView mAccountTextView;
    @BindView(R.id.tv_transaction_type)
    TextView mTransactionTextView;
    @BindView(R.id.tv_currency_type)
    TextView mCurrencyTextView;
    @BindView(R.id.tv_category_label)
    TextView mCategoryTextView;
    @BindView(R.id.et_amount)
    EditText mAmountEditText;

    @State
    int mAccountIndex, mTransactionIndex, mCurrencyIndex, mCategoryIndex;
    private ArrayList<String> mAccountTitles, mTransactionTitles, mCurrencyTitles, mCategoryTitles;

    private AppCompatActivity mActivity;

    public static TransactionCreateFragment newInstance(final int accountIndex) {
        final TransactionCreateFragment fragment = new TransactionCreateFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ACCOUNT_INDEX, accountIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        final ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getArguments() != null) {
            mAccountIndex = getArguments().getInt(ARG_ACCOUNT_INDEX, 0);
        }

        initTitlesLists();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultValues();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle(R.string.new_transaction_title);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                hideKeyboard();
                mActivity.getSupportFragmentManager().popBackStackImmediate();
                return true;
            case R.id.action_accept:
                hideKeyboard();
                createTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initTitlesLists() {
        mAccountTitles = getAccountTitles();
        mTransactionTitles = Utils.getTransactionTitles(mActivity);
        mCurrencyTitles = Utils.getCurrencyTitles(mActivity);
        mCategoryTitles = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.arr_expense_categories)));
    }

    private void setDefaultValues() {
        mAccountTextView.setText(mAccountTitles.get(mAccountIndex));
        mTransactionTextView.setText(mTransactionTitles.get(mTransactionIndex));
        mCurrencyTextView.setText(CurrencyType.values()[mCurrencyIndex].name());
        mCategoryTextView.setText(mCategoryTitles.get(mCategoryIndex));
    }

    //todo replace with db
    private ArrayList<String> getAccountTitles() {
        final List<Account> accounts = AccountsDbStub.getInstance().getAccounts();
        final ArrayList<String> titles = new ArrayList<>();

        for (final Account account : accounts) {
            titles.add(account.getTitle());
        }

        return titles;
    }

    @OnClick({R.id.tv_account_type_title, R.id.tv_account_type})
    void selectAccountType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(mAccountTitles, mAccountIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            mAccountTextView.setText(mAccountTitles.get(selectedIndex));
            mAccountIndex = selectedIndex;
        });
        dialog.show(mActivity.getSupportFragmentManager(), null);
    }

    @OnClick({R.id.tv_transaction_type_title, R.id.tv_transaction_type})
    void selectTransactionType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(mTransactionTitles, mTransactionIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            final String type = mTransactionTitles.get(selectedIndex);
            replaceCategoryList(type);
            mTransactionTextView.setText(type);
            mTransactionIndex = selectedIndex;
        });
        dialog.show(mActivity.getSupportFragmentManager(), null);
    }

    @OnClick({R.id.tv_currency_type_title, R.id.tv_currency_type})
    void selectCurrencyType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(mCurrencyTitles, mCurrencyIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            mCurrencyTextView.setText(CurrencyType.values()[selectedIndex].name());
            mCurrencyIndex = selectedIndex;
        });
        dialog.show(mActivity.getSupportFragmentManager(), null);
    }

    @OnClick({R.id.tv_category_title, R.id.tv_category_label})
    void selectCategoryLabel() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(mCategoryTitles, mCategoryIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            mCategoryTextView.setText(mCategoryTitles.get(selectedIndex));
            mCategoryIndex = selectedIndex;
        });
        dialog.show(mActivity.getSupportFragmentManager(), null);
    }

    private void replaceCategoryList(final String type) {
        if (type.equalsIgnoreCase(getString(R.string.dialog_title_expense))) {
            mCategoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_expense_categories)));
        } else if (type.equalsIgnoreCase(getString(R.string.dialog_title_income))) {
            mCategoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_income_categories)));
        }

        mCategoryIndex = 0;
        mCategoryTextView.setText(mCategoryTitles.get(mCategoryIndex));
    }

    private void createTransaction() {
        //todo implement
    }
}

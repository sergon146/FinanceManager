package com.myst3ry.financemanager.ui.operations;

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
import com.myst3ry.financemanager.R;
import com.myst3ry.financemanager.ui.base.BaseFragment;
import com.myst3ry.financemanager.ui.dialogs.SelectionDialogFragment;
import com.myst3ry.financemanager.utils.Utils;
import com.myst3ry.financemanager.utils.formatter.balance.BalanceFormatterFactory;
import com.myst3ry.model.Account;
import com.myst3ry.model.CurrencyType;
import com.myst3ry.model.Operation;
import com.myst3ry.model.OperationType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public final class OperationCreateFragment extends BaseFragment<OperationCreatePresenter>
        implements OperationCreateView {

    private static final String ACCOUNT_ID = "ACCOUNT_ID";

    @BindView(R.id.account_title)
    TextView title;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.operation_type)
    TextView OperationTextView;
    @BindView(R.id.category_label)
    TextView categoryTextView;
    @BindView(R.id.et_amount)
    EditText amountEditText;

    @Inject
    @InjectPresenter
    OperationCreatePresenter presenter;
    @State
    OperationType currentOperationType;
    @State
    int operationIndex, currencyIndex, categoryIndex;
    private BalanceFormatterFactory formatterFactory = new BalanceFormatterFactory();
    private ArrayList<String> operationTitles, categoryTitles;
    private long accountId;

    public static OperationCreateFragment newInstance(long accountId) {
        final OperationCreateFragment fragment = new OperationCreateFragment();
        final Bundle args = new Bundle();
        args.putSerializable(ACCOUNT_ID, accountId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @ProvidePresenter
    protected OperationCreatePresenter providePresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setScreenTitle(R.string.new_operation_title);

        if (getArguments() != null) {
            accountId = (long) getArguments().getSerializable(ACCOUNT_ID);
            getPresenter().setAccountId(accountId);
        }

        initTitlesLists();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_operation_create, container, false);
    }

    @Override
    protected void prepareViews() {
        setScreenTitle(R.string.new_operation_title);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDefaultValues();
    }

    private void initTitlesLists() {
        operationTitles = Utils.getOperationTitles(getContext());
        categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.arr_expense_categories)));
    }

    private void setDefaultValues() {
        OperationTextView.setText(operationTitles.get(operationIndex));
        categoryTextView.setText(categoryTitles.get(categoryIndex));
        currentOperationType = OperationType.values()[0];
    }

    @OnClick( {R.id.operation_type_title, R.id.operation_type})
    void selectOperationType() {
        final SelectionDialogFragment dialog = SelectionDialogFragment.newInstance(operationTitles, operationIndex);
        dialog.setOnDialogSelectionListener(selectedIndex -> {
            currentOperationType = OperationType.values()[selectedIndex];
            final String type = operationTitles.get(selectedIndex);
            replaceCategoryList(type);
            OperationTextView.setText(type);
            operationIndex = selectedIndex;
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
        if (type.equalsIgnoreCase(getString(R.string.expense))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_expense_categories)));
        } else if (type.equalsIgnoreCase(getString(R.string.income))) {
            categoryTitles = new ArrayList<>(Arrays.asList(getResources()
                    .getStringArray(R.array.arr_income_categories)));
        }

        categoryIndex = 0;
        categoryTextView.setText(categoryTitles.get(categoryIndex));
    }

    @OnClick(R.id.add)
    void onAddClick() {
        createOperation();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            manager.popBackStack();
        }
    }

    private void createOperation() {
        final String amount = amountEditText.getText().toString();
        if (amount.equals(BigDecimal.ZERO)) {
            showToast(R.string.zero_amount);
            return;
        }

        if (!amount.isEmpty()) {
            final Operation operation = Operation.newBuilder()
                    .setCurrencyType(CurrencyType.values()[currencyIndex])
                    .setAmount(new BigDecimal(amount))
                    .setOperationType(currentOperationType)
                    .setCategory(categoryTextView.getText().toString())
                    .setAccountId(accountId)
                    .build();
            getPresenter().performOperation(operation);
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
        showToast(R.string.operation_success);
    }

    @Override
    public String getScreenTag() {
        return "OperationCreateFragment";
    }
}
